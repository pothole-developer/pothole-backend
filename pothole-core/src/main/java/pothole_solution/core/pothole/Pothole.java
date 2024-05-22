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

    @Column(columnDefinition = "geography(Point, 4326)")
    private Point location;

    @Column(nullable = false, length = 2)
    @Convert(converter = ProgressEnumConvertor.class)
    private Progress progress;

    private Integer importance;

    @Builder
    public Pothole(Point location, Progress progress, Integer importance) {
        this.location = location;
        this.progress = progress;
        this.importance = importance;
    }

    public void changeProgress(Progress progress) {
        this.progress = progress;
    }
}
