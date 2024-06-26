package pothole_solution.core.domain.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pothole_solution.core.domain.auth.entity.PrincipalDetails;
import pothole_solution.core.domain.auth.service.AuthService;
import pothole_solution.core.domain.member.dto.MemberBaseResponseDto;
import pothole_solution.core.domain.auth.dto.AuthJoinRequestDto;
import pothole_solution.core.domain.auth.dto.AuthLoginRequestDto;
import pothole_solution.core.global.util.response.BaseResponse;

@Slf4j
@RestController
@RequestMapping("/pothole/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/{roleName}/join")
    public BaseResponse<MemberBaseResponseDto> join(@PathVariable String roleName,
                                                    @Valid @RequestBody final AuthJoinRequestDto authJoinRequestDto) {

        MemberBaseResponseDto responseDto = authService.join(roleName, authJoinRequestDto);

        return new BaseResponse<>(responseDto);
    }

    @PostMapping("/{roleName}/login")
    public BaseResponse<MemberBaseResponseDto> login(@PathVariable String roleName,
                                                     @Valid @RequestBody final AuthLoginRequestDto authLoginRequestDto,
                                                     final HttpServletRequest httpServletRequest) {

        MemberBaseResponseDto responseDto = authService.login(roleName, authLoginRequestDto, httpServletRequest);

        return new BaseResponse<>(responseDto);
    }

    @PostMapping("/{roleName}/logout")
    public BaseResponse<Object> logout(@PathVariable String roleName,
                                       @AuthenticationPrincipal PrincipalDetails principalDetails,
                                       final HttpServletRequest httpServletRequest) {

        Long memberId = principalDetails.getId();

        authService.logout(roleName, memberId, httpServletRequest);

        return new BaseResponse<>();
    }
}