package pothole_solution.manager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pothole_solution.core.global.util.response.BaseResponse;
import pothole_solution.manager.service.PotholeHistoryManagerService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pothole/v1/manager/pothole-history")
public class PotholeHistoryManagerController {
    private final PotholeHistoryManagerService potholeHistoryManagerService;

    @DeleteMapping("/{potholeHistoryId}")
    public BaseResponse<Void> deletePotholeHistory(@PathVariable("potholeHistoryId") Long potholeHistoryId) {
        potholeHistoryManagerService.deletePotholeHistory(potholeHistoryId);

        return new BaseResponse<>();
    }
}
