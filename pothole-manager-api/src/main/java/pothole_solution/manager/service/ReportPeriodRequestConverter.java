package pothole_solution.manager.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;

public class ReportPeriodRequestConverter implements Converter<String, ReportPeriod> {
    @Override
    public ReportPeriod convert(@NotNull String period) {
        return ReportPeriod.enumOf(period);
    }
}
