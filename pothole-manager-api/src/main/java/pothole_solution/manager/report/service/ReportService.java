package pothole_solution.manager.report.service;

import pothole_solution.manager.report.entity.ReportPeriod;
import pothole_solution.manager.report.dto.RespPotDngrCntByPeriodDto;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    List<RespPotDngrCntByPeriodDto> getPeriodPotholeDangerousCount(LocalDate startDate, LocalDate endDate, ReportPeriod period);
}
