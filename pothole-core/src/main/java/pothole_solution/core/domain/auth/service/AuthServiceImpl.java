package pothole_solution.core.domain.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pothole_solution.core.domain.member.dto.MemberBaseResponseDto;
import pothole_solution.core.domain.auth.dto.AuthJoinRequestDto;
import pothole_solution.core.domain.auth.dto.AuthLoginRequestDto;
import pothole_solution.core.domain.member.entity.Member;
import pothole_solution.core.domain.member.entity.Role;
import pothole_solution.core.domain.member.service.MemberService;

import static pothole_solution.core.util.exception.CustomException.MISMATCH_PASSWORD;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final MemberService memberService;
    private final SessionService sessionService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberBaseResponseDto join(AuthJoinRequestDto requestDto) {

        memberService.validateDuplicateEmail(requestDto.getEmail());

        Member joinMember = Member.create(
                requestDto.getName(),
                requestDto.getEmail(),
                passwordEncoder.encode(requestDto.getPassword()),
                requestDto.getContact(),
                Role.valueOf(requestDto.getRole())
        );

        Member saveMember = memberService.save(joinMember);

        return MemberBaseResponseDto.of(saveMember);
    }

    @Override
    public MemberBaseResponseDto login(AuthLoginRequestDto requestDto, HttpServletRequest request) {

        Member findMember = memberService.findByEmail(requestDto.getEmail());

        matchesPassword(requestDto.getPassword(), findMember.getPassword());

        sessionService.create(findMember.getEmail(), findMember.getRole().getRoleName(), request);

        return MemberBaseResponseDto.of(findMember);
    }

    private void matchesPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw MISMATCH_PASSWORD;
        }
    }

    @Override
    public MemberBaseResponseDto logout(Long memberId, HttpServletRequest httpServletRequest) {

        sessionService.invalidate(httpServletRequest);

        return MemberBaseResponseDto.of(memberId);
    }
}
