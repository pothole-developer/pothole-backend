package pothole_solution.manager.report.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AutoPeriodCalculator {
    private static final int CRITERIA_OF_MONTHLY = 3;
    private static final int CRITERIA_OF_WEEKLY = 3;

    protected static boolean isMonthly(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.MONTHS.between(startDate, endDate) >= CRITERIA_OF_MONTHLY;
    }

    protected static boolean isWeekly(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.MONTHS.between(startDate, endDate) < CRITERIA_OF_MONTHLY
                && ChronoUnit.WEEKS.between(startDate, endDate) >= CRITERIA_OF_WEEKLY;
    }
}
