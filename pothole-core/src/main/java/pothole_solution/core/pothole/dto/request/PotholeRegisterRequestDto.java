package pothole_solution.core.pothole.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import pothole_solution.core.pothole.Pothole;
import pothole_solution.core.pothole.Progress;

import java.util.Random;

@Getter
@NoArgsConstructor
public class PotholeRegisterRequestDto {
    private double lat;
    private double lon;

    @Builder
    public PotholeRegisterRequestDto(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Pothole toPothole() {
        GeometryFactory geometryFactory = new GeometryFactory();

        int randomImportance = new Random().nextInt(100);

        return Pothole.builder()
                .location(geometryFactory.createPoint(new Coordinate(lat, lon)))
                .importance(randomImportance)
                .progress(Progress.REGISTER)
                .build();
    }
}
