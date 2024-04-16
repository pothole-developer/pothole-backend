package pothole_solution.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Progress {
    READY("R"),
    IN_PROGRESS("I"),
    DONE("D");

    private String value;
}