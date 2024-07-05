package pothole_solution.core.domain.pothole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pothole_solution.core.domain.pothole.entity.PotholeHistory;

import java.util.List;

public interface PotholeHistoryRepository extends JpaRepository<PotholeHistory, Long> {
    @Query("select ph from PotholeHistory ph " +
            "where ph.pothole.potholeId = :potholeId")
    List<PotholeHistory> findAllByPotholeId(@Param("potholeId") Long potholeId);

    @Modifying
    @Query("delete from PotholeHistory ph " +
            "where ph.pothole.potholeId = :potholeId")
    void deleteAllByPotholeId(@Param("potholeId") Long potholeId);
}
