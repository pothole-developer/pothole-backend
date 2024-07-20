package pothole_solution.manager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pothole_solution.core.domain.pothole.entity.Pothole;
import pothole_solution.core.domain.pothole.entity.PotholeHistory;
import pothole_solution.core.domain.pothole.entity.PotholeHistoryImage;
import pothole_solution.core.domain.pothole.entity.Progress;
import pothole_solution.core.domain.pothole.repository.PotholeHistoryImageRepository;
import pothole_solution.core.domain.pothole.repository.PotholeHistoryRepository;
import pothole_solution.core.infra.s3.ImageService;

import java.util.ArrayList;
import java.util.List;

import static pothole_solution.core.global.exception.CustomException.NONE_POTHOLE_HISTORY;
import static pothole_solution.core.global.exception.CustomException.NONE_PROGRESS_STATUS_IMG;

@Service
@RequiredArgsConstructor
@Transactional
public class PotholeHistoryImageManagerServiceImpl implements PotholeHistoryImageManagerService {
    private final PotholeHistoryImageRepository potholeHistoryImageRepository;
    private final PotholeHistoryRepository potholeHistoryRepository;
    private final ImageService imageService;

    @Override
    public List<PotholeHistoryImage> registerPotholeHistoryImage(Long potholeHistoryId, List<MultipartFile> potholeHistoryImages) {
        PotholeHistory potholeHistory = potholeHistoryRepository.findById(potholeHistoryId)
                                                                .orElseThrow(
                                                                        () -> NONE_POTHOLE_HISTORY
                                                                );

        List<PotholeHistoryImage> potholeHistoryImageList = new ArrayList<>();

        String dirName = uploadDirName(potholeHistory.getPothole().getPotholeId(), potholeHistory.getProcessStatus().getValue());
        List<String> potholeHistoryImagesUrl = imageService.uploadImages(potholeHistoryImages, dirName);

        for (String potholeHistoryImageUrl : potholeHistoryImagesUrl) {
            PotholeHistoryImage createPotholeHistoryImage = new PotholeHistoryImage(potholeHistory, potholeHistoryImageUrl);

            PotholeHistoryImage savedPotholeHistoryImage = potholeHistoryImageRepository.save(createPotholeHistoryImage);

            potholeHistoryImageList.add(savedPotholeHistoryImage);
        }

        // 만약 Image 업로드를 PotholeHistory 의 REGISTER 에 하는데 썸네일이 존재하지 않는다면 현재 등록하는 사진의 첫 번째 사진으로 썸네일 재등록
        reRegisterThumbnail(potholeHistory, potholeHistoryImagesUrl);

        return potholeHistoryImageList;
    }

    private String uploadDirName(Long potholeId, String progressStatus) {
        return potholeId + "/" + progressStatus;
    }

    private void reRegisterThumbnail(PotholeHistory potholeHistory, List<String> potholeHistoryImagesUrl) {
        Pothole pothole = potholeHistory.getPothole();

        if (potholeHistory.getProcessStatus().equals(Progress.REGISTER) && pothole.getThumbnail() == null) {
            pothole.createThumbnail(potholeHistoryImagesUrl.get(0));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public PotholeHistoryImage getPotholeHistoryImage(Long potholeHistoryImageId) {
        return potholeHistoryImageRepository.findById(potholeHistoryImageId)
                                            .orElseThrow(
                                                    () -> NONE_PROGRESS_STATUS_IMG
                                            );
    }

    @Transactional(readOnly = true)
    @Override
    public List<PotholeHistoryImage> getAllPotholeHistoryImageByPotholeId(Long potholeId) {
        return potholeHistoryImageRepository.findAllByPotholeId(potholeId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PotholeHistoryImage> getAllPotholeHistoryImageByPotholeHistoryId(List<PotholeHistory> potholeHistories) {
        List<Long> potholeHistoryIds = potholeHistories.stream()
                                                       .map(PotholeHistory::getPotholeHistoryId)
                                                       .toList();

        return potholeHistoryImageRepository.findAllByPotholeHistoryIds(potholeHistoryIds);
    }

    @Override
    public void deletePotholeHistoryImage(Long potholeHistoryImageId) {
        PotholeHistoryImage potholeHistoryImage = potholeHistoryImageRepository.findById(potholeHistoryImageId)
                                                                               .orElseThrow(
                                                                                       () -> NONE_PROGRESS_STATUS_IMG
                                                                               );

        String potholeHistoryImageUrl = potholeHistoryImage.getImage();
        Pothole pothole = potholeHistoryImage.getPotholeHistory().getPothole();

        potholeHistoryImageRepository.delete(potholeHistoryImage);

        imageService.deleteImage(potholeHistoryImage.getImage());

        // 만약 삭제하는 이미지가 썸네일이라면 Pothole 의 Thumbnail 을 null 로 설정
        potholeThumbnailToNull(pothole, potholeHistoryImageUrl);
    }

    private void potholeThumbnailToNull(Pothole pothole, String deletedImageUrl) {
        String thumbnail = pothole.getThumbnail();

        if (deletedImageUrl.equals(thumbnail)) {
            pothole.changeThumbnail(null);
        }
    }
}
