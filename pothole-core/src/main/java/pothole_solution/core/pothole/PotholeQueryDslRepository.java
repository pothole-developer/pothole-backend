package pothole_solution.core.pothole;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pothole_solution.core.pothole.dto.PotholeFilterDto;

import java.util.List;

import static pothole_solution.core.pothole.QPothole.pothole;

@Repository
@RequiredArgsConstructor
public class PotholeQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<Pothole> findByFilter(PotholeFilterDto potholeFilterDto) {
        return jpaQueryFactory.selectFrom(pothole)
                .where(
                        getImportanceFilter(potholeFilterDto.getMinImportance(), potholeFilterDto.getMaxImportance()),
                        getProgressFilter(potholeFilterDto.getProgress()))
                .fetch();
    }

    private BooleanBuilder getProgressFilter(Progress progress) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 진행 상태 입력
        if (progress != null) {
            booleanBuilder.and(pothole.progress.eq(progress));
        }

        return booleanBuilder;
    }

    private BooleanBuilder getImportanceFilter(Integer minImportance, Integer maxImportance) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 최소, 최대 모두 입력
        if (minImportance != 0 && maxImportance != 100) {
            booleanBuilder.and(pothole.importance.between(minImportance, maxImportance));
        }

        // 최소만 입력
        if (minImportance != 0 && maxImportance == 100) {
            booleanBuilder.and(pothole.importance.goe(minImportance));
        }

        // 최대만 입력
        if (minImportance == 0 && maxImportance != 100) {
            booleanBuilder.and(pothole.importance.loe(maxImportance));
        }

        return booleanBuilder;
    }
}
