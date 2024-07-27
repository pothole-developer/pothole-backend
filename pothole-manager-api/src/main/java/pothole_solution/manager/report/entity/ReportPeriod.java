package pothole_solution.manager.report.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pothole_solution.core.global.exception.CustomException;

@Getter
@AllArgsConstructor
public enum ReportPeriod {
    MONTHLY("YYYY-MM"),
    WEEKLY("YYYY-MM-W"),
    DAILY("YYYY-MM-DD"),
    AUTO("auto");

    private final String queryOfPeriod;

    public static ReportPeriod enumOf(String period) {
        try {
            return ReportPeriod.valueOf(period.toUpperCase());
        } catch (RuntimeException e) {
            throw CustomException.MISMATCH_PERIOD;
        }
    }
}
