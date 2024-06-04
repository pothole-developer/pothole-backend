package pothole_solution.core.domain.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pothole_solution.core.domain.auth.entity.PrincipalDetails;
import pothole_solution.core.domain.auth.service.AuthService;
import pothole_solution.core.domain.member.dto.MemberBaseResponseDto;
import pothole_solution.core.domain.member.dto.MemberJoinRequestDto;
import pothole_solution.core.domain.member.dto.MemberLoginRequestDto;
import pothole_solution.core.util.response.BaseResponse;

@Slf4j
@RestController
@RequestMapping("/pothole/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    public BaseResponse<MemberBaseResponseDto> join(@Valid @RequestBody final MemberJoinRequestDto memberJoinRequestDto) {

        MemberBaseResponseDto responseDto = authService.join(memberJoinRequestDto);

        return new BaseResponse<>(responseDto);
    }

    @PostMapping("/login")
    public BaseResponse<MemberBaseResponseDto> login(@Valid @RequestBody final MemberLoginRequestDto memberLoginRequestDto,
                                                     final HttpServletRequest httpServletRequest) {

        MemberBaseResponseDto responseDto = authService.login(memberLoginRequestDto, httpServletRequest);

        return new BaseResponse<>(responseDto);
    }

    @PostMapping("/logout")
    public BaseResponse<MemberBaseResponseDto> logout(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                     final HttpServletRequest httpServletRequest) {

        Long memberId = principalDetails.getId();
        MemberBaseResponseDto responseDto = authService.logout(memberId, httpServletRequest);

        return new BaseResponse<>(responseDto);
    }
}