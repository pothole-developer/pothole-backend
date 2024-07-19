package pothole_solution.manager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pothole_solution.core.domain.pothole.dto.PotholeFilterDto;
import pothole_solution.core.domain.pothole.dto.request.ChangePotholeProgressStatusRequestDto;
import pothole_solution.core.domain.pothole.entity.Pothole;
import pothole_solution.core.domain.pothole.entity.PotholeHistory;
import pothole_solution.core.domain.pothole.entity.PotholeHistoryImage;
import pothole_solution.core.domain.pothole.repository.PotholeHistoryImageRepository;
import pothole_solution.core.domain.pothole.repository.PotholeHistoryRepository;
import pothole_solution.core.domain.pothole.repository.PotholeQueryDslRepository;
import pothole_solution.core.domain.pothole.repository.PotholeRepository;
import pothole_solution.core.infra.s3.ImageService;

import java.util.ArrayList;
import java.util.List;

import static pothole_solution.core.global.exception.CustomException.NONE_POTHOLE;

@Service
@Transactional
@RequiredArgsConstructor
public class PotholeManagerServiceImpl implements PotholeManagerService {
    private final PotholeRepository potholeRepository;
    private final PotholeHistoryRepository potholeHistoryRepository;
    private final PotholeHistoryImageRepository potholeHistoryImageRepository;
    private final PotholeQueryDslRepository potholeQueryDslRepository;
    private final ImageService imageService;

    @Override
    public Pothole registerPothole(Pothole pothole, List<MultipartFile> registerPotholeImages) {
        // Pothole 저장
        Pothole savedPothole = potholeRepository.save(pothole);

        // 포트홀 등록 이미지 S3에 업로드 및 썸네일 설정
        String dirName = uploadDirName(savedPothole.getPotholeId(), savedPothole.getProcessStatus().getValue());
        List<String> imageUrls = imageService.uploadImages(registerPotholeImages, dirName);
        savedPothole.createThumbnail(imageUrls.get(0));

        // PotholeHistory 생성
        PotholeHistory potholeHistory = new PotholeHistory(savedPothole, pothole.getProcessStatus());
        PotholeHistory savedPotholeHistory = potholeHistoryRepository.save(potholeHistory);

        List<String> imageUrls = imageService.uploadImages(registerPotholeImages, pothole.getPotholeId(), pothole.getProcessStatus().getValue());
        pothole.createThumbnail(imageUrls.get(0));

        // PotholeHistoryImage 생성 및 저장
        for (String imageUrl : imageUrls) {
            PotholeHistoryImage potholeHistoryImage = new PotholeHistoryImage(savedPotholeHistory, imageUrl);
            potholeHistoryImageRepository.save(potholeHistoryImage);
        }

        return savedPothole;
    }

    private String uploadDirName(Long potholeId, String progressStatus) {
        return potholeId + "/" + progressStatus;
    }

    @Override
    @Transactional(readOnly = true)
    public Pothole getPotholeWithPotholeHistoryByPotholeId(Long potholeId) {
        return potholeRepository.findPotholeWithPotholeHistoryByPotholeId(potholeId)
                                .orElseThrow(
                                        () -> NONE_POTHOLE
                                );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pothole> getAllPotholes() {
        return potholeRepository.findAll();
    }

    @Override
    public Pothole changePotholeProgressStatus(Long potholeId, ChangePotholeProgressStatusRequestDto changePotholeProcessStatusRequestDto) {
        Pothole pothole = potholeRepository.findPotholeWithPotholeHistoryByPotholeId(potholeId)
                                           .orElseThrow(
                                                   () -> NONE_POTHOLE
                                           );

        pothole.changeProgress(changePotholeProcessStatusRequestDto.getProgressStatus());

        // 해당 진행 상태에 대한 PotholeHistory 가 없다면 생성
        createPotholeHistoryToProgressStatus(pothole);

        return pothole;
    }

    private void createPotholeHistoryToProgressStatus(Pothole pothole) {
        boolean isProcessStatusIncluded = pothole.getPotholeHistories().stream()
                                                           .anyMatch(potholeHistory ->
                                                                   potholeHistory.getProcessStatus().equals(pothole.getProcessStatus())
                                                           );

        if (!isProcessStatusIncluded) {
            PotholeHistory potholeHistory = new PotholeHistory(pothole, pothole.getProcessStatus());
            potholeHistoryRepository.save(potholeHistory);
        }
    }

    @Override
    public void deletePothole(Long potholeId) {
        Pothole pothole = findByPotholeId(potholeId);

        String thumbnail = pothole.getThumbnail();

        List<String> potholeHistoryImages = getPotholeHistoryImagesByPotholeId(potholeId);

        // PotholeHistoryImage 삭제
        potholeHistoryImageRepository.deleteAllByPotholeId(potholeId);

        // PotholeHistory 삭제
        potholeHistoryRepository.deleteAllByPotholeId(potholeId);

        // Pothole 삭제
        potholeRepository.deleteById(potholeId);

        // S3 PotholeHistoryImage 삭제
        if (!potholeHistoryImages.isEmpty()) {
            potholeHistoryImages.forEach(imageService::deleteImage);
        }

        // S3 Pothole Thumbnail 삭제
        if (thumbnail != null && !thumbnail.isEmpty()) {
            imageService.deleteImage(thumbnail);
        }
    }

    private List<String> getPotholeHistoryImagesByPotholeId(Long potholeId) {
        List<PotholeHistoryImage> potholeHistoryImages = potholeHistoryImageRepository.findAllByPotholeId(potholeId);

        List<String> potholeHistoryImagesUrl = new ArrayList<>();

        if (potholeHistoryImages != null && !potholeHistoryImages.isEmpty()) {
            potholeHistoryImages.forEach(potholeHistoryImage -> potholeHistoryImagesUrl.add(potholeHistoryImage.getImage()));
        }

        return potholeHistoryImagesUrl;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pothole> getFilteredPotholes(PotholeFilterDto potholeFilterDto) {
        Integer availableMinImportance = (potholeFilterDto.getMinImportance() == null) ? 0 : potholeFilterDto.getMinImportance();
        Integer availableMaxImportance = (potholeFilterDto.getMaxImportance() == null) ? 100 : potholeFilterDto.getMaxImportance();

        potholeFilterDto.changeToAvailableImportance(availableMinImportance, availableMaxImportance);

        return potholeQueryDslRepository.findByFilter(potholeFilterDto);
    }

    private Pothole findByPotholeId(Long potholeId) {
        return potholeRepository.findById(potholeId)
                                .orElseThrow(
                                        () -> NONE_POTHOLE
                                );
    }
}
