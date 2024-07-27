package pothole_solution.manager.report.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pothole_solution.manager.report.dto.RespPotDngrCntByPeriodDto;
import pothole_solution.manager.report.entity.ReportPeriod;
import pothole_solution.manager.report.repository.ReportQueryDslRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static pothole_solution.manager.report.service.AutoPeriodCalculator.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportQueryDslRepository reportRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RespPotDngrCntByPeriodDto> getPeriodPotholeDangerousCount(LocalDate startDate, LocalDate endDate, ReportPeriod reportPeriod) {

        String queryOfPeriod = getQueryOfPeriod(startDate, endDate, reportPeriod);

        return reportRepository.getPotDngrCntByPeriod(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX), queryOfPeriod);

    }

    private String getQueryOfPeriod(LocalDate startDate, LocalDate endDate, ReportPeriod reportPeriod) {
        if (!reportPeriod.equals(ReportPeriod.AUTO)) {
            return reportPeriod.getQueryOfPeriod();
        }
        if (isMonthly(startDate, endDate)) {
            return ReportPeriod.MONTHLY.getQueryOfPeriod();
        }
        if (isWeekly(startDate, endDate)) {
            return ReportPeriod.WEEKLY.getQueryOfPeriod();
        }
        return ReportPeriod.DAILY.getQueryOfPeriod();
    }

}
