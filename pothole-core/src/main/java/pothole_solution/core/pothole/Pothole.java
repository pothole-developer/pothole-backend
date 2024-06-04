package pothole_solution.core.pothole;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import pothole_solution.core.util.convertor.ProgressEnumConvertor;

@Entity
@Getter
@NoArgsConstructor
public class Pothole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long potholeId;

    @Column(length = 50)
    private String roadName;

    @Column(nullable = false, columnDefinition = "geography(Point, 4326)")
    private Point point;

    @Column(columnDefinition = "TEXT")
    private String thumbnail;

    private Short importance;

    @Column(nullable = false, length = 5)
    @Convert(converter = ProgressEnumConvertor.class)
    private Progress processStatus;

    @Builder
    public Pothole(String roadName, Point point, String thumbnail, Short importance, Progress processStatus) {
        this.roadName = roadName;
        this.point = point;
        this.thumbnail = thumbnail;
        this.importance = importance;
        this.processStatus = processStatus;
    }

    public void changeProgress(Progress processStatus) {
        this.processStatus = processStatus;
    }
}
