package pothole_solution.manager.service;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    List<PeriodPotholeCountDto> getPeriodPotholeDangerousCount(LocalDate startDate, LocalDate endDate, ReportPeriod period);
}
