package pothole_solution.core;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PotholeRepository extends JpaRepository<Pothole, Long> {
}