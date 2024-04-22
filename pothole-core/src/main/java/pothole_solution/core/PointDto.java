package pothole_solution.core;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class PointDto {
    public final Long pothole_id;
    public final double lat;
    public final double lon;
    public final Progress progress;

    public PointDto(Pothole pothole) {
        this.pothole_id = pothole.getPothole_id();
        this.lat = pothole.getLocation().getY();
        this.lon = pothole.getLocation().getX();
        this.progress = pothole.getProgress();
    }
}
