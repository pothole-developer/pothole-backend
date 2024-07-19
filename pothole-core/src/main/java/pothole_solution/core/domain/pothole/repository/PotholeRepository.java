package pothole_solution.core.domain.pothole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pothole_solution.core.domain.pothole.entity.Pothole;

import java.util.Optional;

public interface PotholeRepository extends JpaRepository<Pothole, Long> {
    @Query("select p from Pothole p " +
            "join fetch p.potholeHistories ph " +
            "where p.potholeId = :potholeId")
    Optional<Pothole> findPotholeWithPotholeHistoryByPotholeId(@Param("potholeId") Long potholeId);
}
