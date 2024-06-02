package pothole_solution.core.pothole.dto;

import lombok.Builder;
import lombok.Getter;
import pothole_solution.core.pothole.Progress;

@Getter
public class PotholeFilterDto {
    private Short minImportance;
    private Short maxImportance;
    private final Progress processStatus;

    @Builder
    public PotholeFilterDto(Short minImportance, Short maxImportance, Progress processStatus) {
        this.minImportance = minImportance;
        this.maxImportance = maxImportance;
        this.processStatus = processStatus;
    }

    public void changeToAvailableImportance(Short availableMinImportance, Short availableMaxImportance) {
        this.minImportance = availableMinImportance;
        this.maxImportance = availableMaxImportance;
    }
}
