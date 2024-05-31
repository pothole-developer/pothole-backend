package pothole_solution.manager.sample.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pothole_solution.core.domain.member.dto.MemberJoinRequestDto;
import pothole_solution.core.domain.member.dto.MemberLoginRequestDto;
import pothole_solution.core.domain.member.dto.MemberBaseResponseDto;
import pothole_solution.core.domain.member.service.MemberServiceImpl;
import pothole_solution.core.util.response.BaseResponse;

@Slf4j
@RestController
@RequestMapping("/pothole/v1/manager")
@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImpl memberServiceImpl;

    @PostMapping("/join")
    public BaseResponse<MemberBaseResponseDto> join(@Valid @RequestBody final MemberJoinRequestDto memberJoinRequestDto) {

        MemberBaseResponseDto responseDto = memberServiceImpl.join(memberJoinRequestDto);

        return new BaseResponse<>(responseDto);
    }

    @PostMapping("/login")
    public BaseResponse<MemberBaseResponseDto> login(@Valid @RequestBody final MemberLoginRequestDto memberLoginRequestDto,
                                                     final HttpServletRequest httpServletRequest) {

        MemberBaseResponseDto responseDto = memberServiceImpl.login(memberLoginRequestDto, httpServletRequest);

        return new BaseResponse<>(responseDto);
    }

    @PostMapping("/logout")
    public BaseResponse<String> logout(final HttpServletRequest httpServletRequest) {

        String logout = memberServiceImpl.logout(httpServletRequest);

        return new BaseResponse<>(logout);
    }
}