package pothole_solution.manager.service;

import org.springframework.web.multipart.MultipartFile;
import pothole_solution.core.domain.pothole.entity.PotholeHistory;
import pothole_solution.core.domain.pothole.entity.PotholeHistoryImage;

import java.util.List;

public interface PotholeHistoryImageManagerService {
    List<PotholeHistoryImage> registerPotholeHistoryImage(Long potholeHistoryId, List<MultipartFile> potholeHistoryImages);

    PotholeHistoryImage getPotholeHistoryImage(Long potholeHistoryImageId);

    List<PotholeHistoryImage> getAllPotholeHistoryImageByPotholeId(Long potholeId);

    List<PotholeHistoryImage> getAllPotholeHistoryImageByPotholeHistoryId(List<PotholeHistory> potholeHistories);

    void deletePotholeHistoryImage(Long potholeHistoryImageId);
}
