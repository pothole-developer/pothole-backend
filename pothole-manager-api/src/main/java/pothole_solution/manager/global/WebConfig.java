package pothole_solution.manager.global;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pothole_solution.manager.service.ReportPeriodRequestConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(@NotNull FormatterRegistry registry) {
        registry.addConverter(new ReportPeriodRequestConverter());
    }
}
