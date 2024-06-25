package pothole_solution.core.global.util.convertor;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import pothole_solution.core.domain.pothole.entity.Progress;

import java.util.Arrays;

@Converter(autoApply = true)
public class ProgressEnumConvertor implements AttributeConverter<Progress, String> {
    @Override
    public String convertToDatabaseColumn(Progress attribute) {
        return attribute.getValue();
    }

    @Override
    public Progress convertToEntityAttribute(String dbData) {
        return Arrays.stream(Progress.values())
                .filter(p -> p.getValue().equals(dbData))
                .findFirst()
                .orElse(null);
    }
}
