package pothole_solution.core.domain.member.service;

import jakarta.servlet.http.HttpServletRequest;
import pothole_solution.core.domain.member.dto.MemberJoinRequestDto;
import pothole_solution.core.domain.member.dto.MemberLoginRequestDto;
import pothole_solution.core.domain.member.dto.MemberBaseResponseDto;
import pothole_solution.core.domain.member.entity.Member;

public interface MemberService {
    Member save(Member joinMember);
    Member findByEmail(String email);
    boolean existsByEmail(String email);
    void validateDuplicateEmail(String email);
    MemberBaseResponseDto join(MemberJoinRequestDto requestDto);
    MemberBaseResponseDto login(MemberLoginRequestDto requestDto,
                                HttpServletRequest httpServletRequest);
    String logout(HttpServletRequest httpServletRequest);
}
