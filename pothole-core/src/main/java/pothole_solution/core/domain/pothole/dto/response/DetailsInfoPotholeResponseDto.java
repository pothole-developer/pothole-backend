package pothole_solution.core.domain.pothole.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pothole_solution.core.domain.pothole.entity.Pothole;
import pothole_solution.core.domain.pothole.entity.Progress;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class DetailsInfoPotholeResponseDto {
    private Long potholeId;
    private String roadName;
    private double lat;
    private double lon;
    private String thumbnail;
    private Integer importance;
    private Progress progressStatus;
    private Integer dangerous;

    @JsonProperty(value = "potholeHistories")
    private List<GetPotholeHistoryResponseDto> potholeHistoryResponseDto = new ArrayList<>();

    @Builder
    public DetailsInfoPotholeResponseDto(Pothole pothole, List<GetPotholeHistoryResponseDto> getPotholeHistoryResponseDtoList) {
        this.potholeId = pothole.getPotholeId();
        this.roadName = pothole.getRoadName();
        this.lat = pothole.getPoint().getY();
        this.lon = pothole.getPoint().getX();
        this.thumbnail = pothole.getThumbnail();
        this.importance = pothole.getImportance();
        this.progressStatus = pothole.getProcessStatus();
        this.dangerous = pothole.getDangerous();
        this.potholeHistoryResponseDto = getPotholeHistoryResponseDtoList;
    }
}