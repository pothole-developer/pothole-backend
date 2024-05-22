package pothole_solution.core.pothole.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pothole_solution.core.pothole.Pothole;
import pothole_solution.core.pothole.Progress;

@Getter
@NoArgsConstructor
public class PotholeResponseDto {
    private double lat;
    private double lon;
    private int importance;
    private Progress progress;

    @Builder
    public PotholeResponseDto(Pothole pothole) {
        this.lat = pothole.getLocation().getY();
        this.lon = pothole.getLocation().getX();
        this.importance = pothole.getImportance();
        this.progress = pothole.getProgress();
    }
}
