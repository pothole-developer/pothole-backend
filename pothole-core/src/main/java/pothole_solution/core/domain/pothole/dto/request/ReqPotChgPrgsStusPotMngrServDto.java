package pothole_solution.core.domain.pothole.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pothole_solution.core.domain.pothole.entity.Progress;
import pothole_solution.core.global.util.valid.enums.Enum;

@Getter
@NoArgsConstructor
public class ReqPotChgPrgsStusPotMngrServDto {
    @Enum(enumClass = Progress.class)
    private Progress progressStatus;

    @Builder
    public ReqPotChgPrgsStusPotMngrServDto(Progress progressStatus) {
        this.progressStatus = progressStatus;
    }
}
