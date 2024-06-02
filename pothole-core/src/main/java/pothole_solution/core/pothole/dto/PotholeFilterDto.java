package pothole_solution.core.pothole.dto;

import lombok.Builder;
import lombok.Getter;
import pothole_solution.core.pothole.Progress;

@Getter
public class PotholeFilterDto {
    private Integer minImportance;
    private Integer maxImportance;
    private final Progress progress;

    @Builder
    public PotholeFilterDto(Integer minImportance, Integer maxImportance, Progress progress) {
        this.minImportance = minImportance;
        this.maxImportance = maxImportance;
        this.progress = progress;
    }

    public void changeToAvailableImportance(Integer availableMinImportance, Integer availableMaxImportance) {
        this.minImportance = availableMinImportance;
        this.maxImportance = availableMaxImportance;
    }
}
