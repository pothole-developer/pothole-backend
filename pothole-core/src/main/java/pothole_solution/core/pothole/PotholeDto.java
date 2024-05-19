package pothole_solution.core.pothole;

import lombok.Data;

@Data
public class PotholeDto {
    public final Long potholeId;
    public final double lat;
    public final double lon;
    public final int importance;
    public final Progress progress;

    public PotholeDto(Pothole pothole) {
        this.potholeId = pothole.getPotholeId();
        this.lat = pothole.getLocation().getY();
        this.lon = pothole.getLocation().getX();
        this.importance = pothole.getImportance();
        this.progress = pothole.getProgress();
    }
}
