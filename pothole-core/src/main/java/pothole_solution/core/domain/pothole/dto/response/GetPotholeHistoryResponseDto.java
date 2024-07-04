package pothole_solution.core.domain.pothole.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pothole_solution.core.domain.pothole.entity.PotholeHistory;
import pothole_solution.core.domain.pothole.entity.PotholeHistoryImage;
import pothole_solution.core.domain.pothole.entity.Progress;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class GetPotholeHistoryResponseDto {
    private Long potholeHistoryId;

    private Progress progressStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private List<GetPotholeHistoryImageResponseDto> potholeHistoryImages = new ArrayList<>();



    @Builder
    public GetPotholeHistoryResponseDto(PotholeHistory potholeHistory, List<PotholeHistoryImage> potholeHistoryImages) {
        this.potholeHistoryId = potholeHistory.getPotholeHistoryId();
        this.progressStatus = potholeHistory.getProcessStatus();
        this.potholeHistoryImages = potholeHistoryImages.stream()
                .map(GetPotholeHistoryImageResponseDto::new)
                .toList();
        this.createdAt = potholeHistory.getCreatedAt();
    }
}
