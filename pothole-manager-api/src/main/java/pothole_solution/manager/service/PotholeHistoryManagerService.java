package pothole_solution.manager.service;

import pothole_solution.core.domain.pothole.entity.PotholeHistory;

import java.util.List;

public interface PotholeHistoryManagerService {
    List<PotholeHistory> getAllPotholeHistoryByPotholeId(Long potholeId);

    void deletePotholeHistory(Long potholeHistoryId);
}
