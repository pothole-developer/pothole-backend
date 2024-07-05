package pothole_solution.manager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pothole_solution.core.domain.pothole.entity.Pothole;
import pothole_solution.core.domain.pothole.entity.PotholeHistory;
import pothole_solution.core.domain.pothole.entity.PotholeHistoryImage;
import pothole_solution.core.domain.pothole.entity.Progress;
import pothole_solution.core.domain.pothole.repository.PotholeHistoryImageRepository;
import pothole_solution.core.domain.pothole.repository.PotholeHistoryRepository;
import pothole_solution.core.infra.s3.ImageService;

import java.util.List;

import static pothole_solution.core.global.exception.CustomException.NONE_POTHOLE_HISTORY;

@Service
@Transactional
@RequiredArgsConstructor
public class PotholeHistoryManagerServiceImpl implements PotholeHistoryManagerService {
    private final PotholeHistoryRepository potholeHistoryRepository;
    private final PotholeHistoryImageRepository potholeHistoryImageRepository;
    private final ImageService imageService;

    @Override
    public List<PotholeHistory> getAllPotholeHistoryByPotholeId(Long potholeId) {
        return potholeHistoryRepository.findAllByPotholeId(potholeId);
    }

    @Override
    public void deletePotholeHistory(Long potholeHistoryId) {
        PotholeHistory potholeHistory = potholeHistoryRepository.findById(potholeHistoryId)
                                                                .orElseThrow(
                                                                        () -> NONE_POTHOLE_HISTORY
                                                                );

        Progress potholeHistoryProgressStatus = potholeHistory.getProcessStatus();

        List<PotholeHistoryImage> potholeHistoryImages =  potholeHistoryImageRepository.findAllByPotholeHistoryId(potholeHistory.getPotholeHistoryId());

        Pothole pothole = potholeHistory.getPothole();

        // PotholeHistoryImage 삭제
        potholeHistoryImageRepository.deleteAllByPotholeHistoryId(potholeHistoryId);

        // PotholeHistory 삭제
        potholeHistoryRepository.delete(potholeHistory);

        // S3 이미지 삭제
        for (PotholeHistoryImage potholeHistoryImage : potholeHistoryImages) {
            imageService.deleteImage(potholeHistoryImage.getImage());
        }

        // 삭제된 PotholeHistory 가 Register 라면 썸네일 삭제
        potholeThumbnailToNull(pothole, potholeHistoryProgressStatus);
    }

    private void potholeThumbnailToNull(Pothole pothole, Progress potholeHistoryProgressStatus) {
        if (potholeHistoryProgressStatus.equals(Progress.REGISTER)) {
            pothole.changeThumbnail(null);
        }
    }
}
