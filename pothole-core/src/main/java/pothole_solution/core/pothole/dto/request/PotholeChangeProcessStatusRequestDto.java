package pothole_solution.core.pothole.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pothole_solution.core.pothole.Progress;

@Getter
@NoArgsConstructor
public class PotholeChangeProcessStatusRequestDto {
    private Progress processStatus;

    @Builder
    public PotholeChangeProcessStatusRequestDto(Progress processStatus) {
        this.processStatus = processStatus;
    }
}
