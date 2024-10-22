package pothole_solution.core.domain.pothole.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import pothole_solution.core.domain.pothole.entity.Pothole;
import pothole_solution.core.domain.pothole.entity.Progress;

@Getter
@NoArgsConstructor
public class ReqPotRegPotMngrServDto {
    private String roadName = "None";

    @NotNull(message = "위도의 값은 반드시 존재해야 합니다.")
    private double lat;

    @NotNull(message = "경도의 값은 반드시 존재해야 합니다.")
    private double lon;

    private Integer dangerous;

    private Integer importance;

    @Builder
    public ReqPotRegPotMngrServDto(String roadName, double lat, double lon, Integer dangerous, Integer importance) {
        if (roadName != null) {
            this.roadName = roadName;
        }

        this.lat = lat;
        this.lon = lon;
        this.importance = importance;
        this.dangerous = dangerous;
    }

    public Pothole toPothole() {
        GeometryFactory geometryFactory = new GeometryFactory();

        return Pothole.builder()
                .roadName(roadName)
                .point(geometryFactory.createPoint(new Coordinate(lon, lat)))
                .importance(importance)
                .dangerous(dangerous)
                .processStatus(Progress.REGISTER)
                .build();
    }
}
