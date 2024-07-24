package pothole_solution.manager.report.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import pothole_solution.manager.report.dto.RespPotDngrCntByPeriodDto;

import java.time.LocalDateTime;
import java.util.List;

import static pothole_solution.core.domain.pothole.entity.QPothole.pothole;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ReportQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<RespPotDngrCntByPeriodDto> getPotDngrCntByPeriod(LocalDateTime startDate, LocalDateTime endDate, String queryOfPeriod) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(RespPotDngrCntByPeriodDto.class,
                                convertDateFormat(queryOfPeriod),
                                countDangerousBetween(1, 20),
                                countDangerousBetween(21, 40),
                                countDangerousBetween(41, 60),
                                countDangerousBetween(61, 80),
                                countDangerousBetween(81, 100)
                        )
                )
                .from(pothole)
                .where(pothole.createdAt.between(startDate, endDate))
                .groupBy(convertDateFormat(queryOfPeriod))
                .orderBy(convertDateFormat(queryOfPeriod).asc())
                .fetch();
    }

    private static StringTemplate convertDateFormat(String queryOfPeriod) {
        return Expressions.stringTemplate(
                    "to_char({0},'" + queryOfPeriod + "')"
                    , pothole.createdAt);
    }

    private static NumberExpression<Long> countDangerousBetween(int from, int to) {
        return Expressions.cases()
                .when(pothole.dangerous.between(from, to))
                .then(1L)
                .otherwise(0L)
                .sum();
    }

}
