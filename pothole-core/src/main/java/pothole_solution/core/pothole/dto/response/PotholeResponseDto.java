package pothole_solution.core.pothole.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pothole_solution.core.pothole.Pothole;
import pothole_solution.core.pothole.Progress;

@Getter
@NoArgsConstructor
public class PotholeResponseDto {
    private String roadName;
    private double lat;
    private double lon;
    private String thumbnail;
    private short importance;
    private Progress processStatus;

    @Builder
    public PotholeResponseDto(Pothole pothole) {
        this.roadName = pothole.getRoadName();
        this.lat = pothole.getPoint().getY();
        this.lon = pothole.getPoint().getX();
        this.thumbnail = pothole.getThumbnail();
        this.importance = pothole.getImportance();
        this.processStatus = pothole.getProcessStatus();
    }
}
