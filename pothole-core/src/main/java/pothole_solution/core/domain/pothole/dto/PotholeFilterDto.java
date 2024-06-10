package pothole_solution.core.domain.pothole.dto;

import lombok.Builder;
import lombok.Getter;
import pothole_solution.core.domain.pothole.entity.Progress;

@Getter
public class PotholeFilterDto {
    private Integer minImportance;
    private Integer maxImportance;
    private final Progress processStatus;

    @Builder
    public PotholeFilterDto(Integer minImportance, Integer maxImportance, Progress processStatus) {
        this.minImportance = minImportance;
        this.maxImportance = maxImportance;
        this.processStatus = processStatus;
    }

    public void changeToAvailableImportance(Integer availableMinImportance, Integer availableMaxImportance) {
        this.minImportance = availableMinImportance;
        this.maxImportance = availableMaxImportance;
    }
}
