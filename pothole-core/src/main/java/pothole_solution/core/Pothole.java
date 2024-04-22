package pothole_solution.core;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import util.convertor.ProgressEnumConvertor;

@Entity
@Getter
@NoArgsConstructor
public class Pothole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pothole_id;

    @Column(columnDefinition = "geography(Point, 4326)")
    private Point location;

    @Column(columnDefinition = "char")
    @Convert(converter = ProgressEnumConvertor.class)
    private Progress progress;

    @Builder
    public Pothole(Point location, Progress progress) {
        this.location = location;
        this.progress = progress;
    }

    public void changeProgress(Progress progress) {
        this.progress = progress;
    }
}
