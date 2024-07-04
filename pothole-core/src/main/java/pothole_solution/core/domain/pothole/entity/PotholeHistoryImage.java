package pothole_solution.core.domain.pothole.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pothole_solution.core.domain.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "POTHOLE_HISTORY_IMG")
public class PotholeHistoryImage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long potholeHistoryImgId;

    @Column(columnDefinition = "TEXT")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pothole_history_id")
    private PotholeHistory potholeHistory;

    @Builder
    public PotholeHistoryImage(String image, PotholeHistory potholeHistory) {
        this.image = image;
        this.potholeHistory = potholeHistory;
    }
}
