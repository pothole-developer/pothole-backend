package pothole_solution.core.domain.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import pothole_solution.core.domain.member.dto.MemberBaseResponseDto;
import pothole_solution.core.domain.auth.dto.AuthJoinRequestDto;
import pothole_solution.core.domain.auth.dto.AuthLoginRequestDto;

public interface AuthService {
    MemberBaseResponseDto join(String roleName, AuthJoinRequestDto requestDto);
    MemberBaseResponseDto login(String roleName, AuthLoginRequestDto requestDto, HttpServletRequest httpServletRequest);
    MemberBaseResponseDto logout(String roleName, Long memberId, HttpServletRequest httpServletRequest);
}
