package pothole_solution.core.global.util.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pothole_solution.core.domain.pothole.entity.Progress;

@Component
public class ProgressUpperConverter implements Converter<String, Progress> {
    @Override
    public Progress convert(String source) {
        return Progress.valueOf(source.toUpperCase());
    }
}
