package pothole_solution.core.domain.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import pothole_solution.core.domain.member.dto.MemberBaseResponseDto;
import pothole_solution.core.domain.member.dto.MemberJoinRequestDto;
import pothole_solution.core.domain.member.dto.MemberLoginRequestDto;

public interface AuthService {
    MemberBaseResponseDto join(MemberJoinRequestDto requestDto);
    MemberBaseResponseDto login(MemberLoginRequestDto requestDto, HttpServletRequest httpServletRequest);
    MemberBaseResponseDto logout(Long memberId, HttpServletRequest httpServletRequest);
}
