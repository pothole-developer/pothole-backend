package pothole_solution.core.pothole.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import pothole_solution.core.pothole.Pothole;
import pothole_solution.core.pothole.Progress;

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

        short randomImportance = (short) (Math.random() * 101);

        return Pothole.builder()
                .roadName("강남로 1")
                .point(geometryFactory.createPoint(new Coordinate(lat, lon)))
                .importance(randomImportance)
                .processStatus(Progress.REGISTER)
                .build();
    }
}
