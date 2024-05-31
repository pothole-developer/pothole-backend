package pothole_solution.core.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pothole_solution.core.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
