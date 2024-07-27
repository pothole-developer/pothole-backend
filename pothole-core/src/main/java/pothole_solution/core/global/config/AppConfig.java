package pothole_solution.core.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pothole_solution.core.global.util.formatter.LocalDateFormatter;

@Configuration
public class AppConfig {
    @Bean
    public LocalDateFormatter localDateFormatter() {
        return new LocalDateFormatter();
    }

}
