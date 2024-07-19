package pothole_solution.core.domain.pothole.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pothole_solution.core.domain.BaseTimeEntity;
import pothole_solution.core.global.util.converter.ProgressEnumConverter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class PotholeHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long potholeHistoryId;

    @Column(nullable = false, length = 5)
    @Convert(converter = ProgressEnumConverter.class)
    private Progress processStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pothole_id")
    private Pothole pothole;

    @OneToMany(mappedBy = "potholeHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PotholeHistoryImage> potholeHistoryImages = new ArrayList<>();

    @Builder
    public PotholeHistory(Pothole pothole, Progress processStatus) {
        this.pothole = pothole;
        this.processStatus = processStatus;

        if (pothole != null) {
            pothole.getPotholeHistories().add(this);
        }
    }
}
