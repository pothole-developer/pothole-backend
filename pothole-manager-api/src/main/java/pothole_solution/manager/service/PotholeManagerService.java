package pothole_solution.manager.service;

import org.springframework.web.multipart.MultipartFile;
import pothole_solution.core.domain.pothole.dto.PotholeFilterDto;
import pothole_solution.core.domain.pothole.dto.request.ChangePotholeProgressStatusRequestDto;
import pothole_solution.core.domain.pothole.entity.Pothole;

import java.util.List;

public interface PotholeManagerService {
    Pothole registerPothole(Pothole pothole, List<MultipartFile> potholeImages);

    Pothole getPotholeWithPotholeHistoryByPotholeId(Long potholeId);

    List<Pothole> getAllPotholes();

    Pothole changePotholeProgressStatus(Long potholeId, ChangePotholeProgressStatusRequestDto changePotholeProcessStatusRequestDto);

    void deletePothole(Long potholeId);

    List<Pothole> getFilteredPotholes(PotholeFilterDto potholeFilterDto);
}
