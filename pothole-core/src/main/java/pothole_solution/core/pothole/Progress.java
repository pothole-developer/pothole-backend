package pothole_solution.core.pothole;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
}