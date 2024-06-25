package pothole_solution.core.domain.pothole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pothole_solution.core.domain.pothole.entity.Pothole;

public interface PotholeRepository extends JpaRepository<Pothole, Long> {
}