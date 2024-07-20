package pothole_solution.core.domain.pothole.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Progress {
    REGISTER("R"),
    EMERGENCY_ONGOING("EO"),
    EMERGENCY_COMPLETE("EC"),
    ONGOING("O"),
    COMPLETE("C"),
    STOP("S");

    private String value;

    @JsonCreator
    public static Progress from(String inputValue) {
        return Stream.of(Progress.values())
                .filter(progress ->
                        progress.toString().equals(inputValue.toUpperCase())
                )
                .findFirst()
                .orElse(null);
    }
}