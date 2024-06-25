package pothole_solution.core.domain.pothole.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import pothole_solution.core.global.util.convertor.ProgressEnumConvertor;

@Entity
@Getter
@NoArgsConstructor
public class Pothole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long potholeId;

    @Column(length = 50)
    private String roadName;

    private String addressName;

    @Column(nullable = false, columnDefinition = "geography(Point, 4326)")
    private Point point;

    @Column(columnDefinition = "TEXT")
    private String thumbnail;

    private Integer importance;

    @Column(nullable = false, length = 5)
    @Convert(converter = ProgressEnumConvertor.class)
    private Progress processStatus;

    private Integer dangerous;

    @Builder
    public Pothole(String roadName, String addressName, Point point, String thumbnail, Integer importance, Progress processStatus, Integer dangerous) {
        this.roadName = roadName;
        this.addressName = addressName;
        this.point = point;
        this.thumbnail = thumbnail;
        this.importance = importance;
        this.processStatus = processStatus;
        this.dangerous = dangerous;
    }

    public void changeProgress(Progress processStatus) {
        this.processStatus = processStatus;
    }

    public void createThumbnailURL(String thumbnailURL) {
        this.thumbnail = thumbnailURL;
    }
}
