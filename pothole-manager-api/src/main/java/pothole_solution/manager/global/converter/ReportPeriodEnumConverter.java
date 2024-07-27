package pothole_solution.manager.global.converter;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import pothole_solution.manager.report.entity.ReportPeriod;

public class ReportPeriodEnumConverter implements Converter<String, ReportPeriod> {
    @Override
    public ReportPeriod convert(@NotNull String period) {
        return ReportPeriod.enumOf(period);
    }
}
