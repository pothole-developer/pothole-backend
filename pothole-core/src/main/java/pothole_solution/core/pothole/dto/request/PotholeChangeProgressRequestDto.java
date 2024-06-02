package pothole_solution.core.pothole.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pothole_solution.core.pothole.Progress;

@Getter
@NoArgsConstructor
public class PotholeChangeProgressRequestDto {
    private Progress progress;

    @Builder
    public PotholeChangeProgressRequestDto(Progress progress) {
        this.progress = progress;
    }
}
