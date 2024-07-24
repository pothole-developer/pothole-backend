package pothole_solution.manager.report.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pothole_solution.core.global.util.response.BaseResponse;
import pothole_solution.manager.report.dto.RespPotDngrCntByPeriodDto;
import pothole_solution.manager.report.entity.ReportPeriod;
import pothole_solution.manager.report.service.ReportService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/pothole/v1/manager")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/pothole-report")
    public BaseResponse<List<RespPotDngrCntByPeriodDto>> getPotDngrCntByPeriod(@RequestParam(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                                               @RequestParam(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                                                               @RequestParam(value = "reportPeriod") ReportPeriod reportPeriod) {

        List<RespPotDngrCntByPeriodDto> periodPotholeCounts = reportService.getPeriodPotholeDangerousCount(startDate, endDate, reportPeriod);

        return new BaseResponse<>(periodPotholeCounts);
    }
}
