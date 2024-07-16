package pothole_solution.core.domain.pothole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pothole_solution.core.domain.pothole.entity.PotholeHistoryImage;

import java.util.List;

public interface PotholeHistoryImageRepository extends JpaRepository<PotholeHistoryImage, Long> {
    @Query("select phi from PotholeHistoryImage phi " +
            "where phi.potholeHistory.pothole.potholeId = :potholeId")
    List<PotholeHistoryImage> findAllByPotholeId(@Param("potholeId") Long potholeId);

    @Modifying
    @Query("delete from PotholeHistoryImage phi " +
            "where phi.potholeHistory.pothole.potholeId = :potholeId")
    void deleteAllByPotholeId(@Param("potholeId") Long potholeId);

    @Query("select phi from PotholeHistoryImage phi " +
            "where phi.potholeHistory.potholeHistoryId = :potholeHistoryId")
    List<PotholeHistoryImage> findAllByPotholeHistoryId(@Param("potholeHistoryId") Long potholeHistoryId);

    @Modifying
    @Query("delete from PotholeHistoryImage phi " +
            "where phi.potholeHistory.potholeHistoryId = :potholeHistoryId")
    void deleteAllByPotholeHistoryId(@Param("potholeHistoryId") Long potholeHistoryId);

    @Query("select phi from PotholeHistoryImage phi " +
            "where phi.potholeHistory.potholeHistoryId in :potholeHistoryIds")
    List<PotholeHistoryImage> findAllByPotholeHistoryIds(@Param("potholeHistoryIds") List<Long> potholeHistoryIds);
}
