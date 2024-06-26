package pothole_solution.core.domain.member.service;

import pothole_solution.core.domain.member.entity.Member;

public interface MemberService {
    Member save(Member joinMember);
    Member findById(Long memberId);
    Member findByEmail(String email);
    boolean existsByEmail(String email);
    void validateDuplicateEmail(String email);
}
