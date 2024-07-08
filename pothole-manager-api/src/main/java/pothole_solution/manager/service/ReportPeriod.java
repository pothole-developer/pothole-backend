package pothole_solution.manager.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pothole_solution.core.global.exception.CustomException;

@Getter
@AllArgsConstructor
public enum ReportPeriod {
    MONTHLY("%Y-%M"),
    WEEKLY("%Y-%W"),
    DAILY("%Y-%M-%D");

    private final String queryOfPeriod;

    public static ReportPeriod enumOf(String period) {
        try {
            return ReportPeriod.valueOf(period.toUpperCase());
        } catch (RuntimeException e) {
            throw CustomException.MISMATCH_PERIOD;
        }
    }
}
