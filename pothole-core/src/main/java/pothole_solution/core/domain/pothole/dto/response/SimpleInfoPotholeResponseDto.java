package pothole_solution.core.domain.pothole.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pothole_solution.core.domain.pothole.entity.Pothole;
import pothole_solution.core.domain.pothole.entity.Progress;

@Getter
@NoArgsConstructor
public class SimpleInfoPotholeResponseDto {
    private Long potholeId;
    private String roadName;
    private double lat;
    private double lon;
    private String thumbnail;
    private Integer importance;
    private Progress processStatus;

    @Builder
    public SimpleInfoPotholeResponseDto(Pothole pothole) {
        this.potholeId = pothole.getPotholeId();
        this.roadName = pothole.getRoadName();
        this.lat = pothole.getPoint().getY();
        this.lon = pothole.getPoint().getX();
        this.thumbnail = pothole.getThumbnail();
        this.importance = pothole.getImportance();
        this.processStatus = pothole.getProcessStatus();
    }
}
