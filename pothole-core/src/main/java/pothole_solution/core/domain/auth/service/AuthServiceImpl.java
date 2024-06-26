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
import pothole_solution.core.global.exception.CustomException;

import static pothole_solution.core.global.exception.CustomException.MISMATCH_PASSWORD;
import static pothole_solution.core.global.exception.CustomException.MISMATCH_ROLE;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final MemberService memberService;
    private final SessionService sessionService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberBaseResponseDto join(String roleName, AuthJoinRequestDto requestDto) {

        memberService.validateDuplicateEmail(requestDto.getEmail());

        Member joinMember = Member.create(
                requestDto.getName(),
                requestDto.getEmail(),
                passwordEncoder.encode(requestDto.getPassword()),
                requestDto.getContact(),
                Role.from(roleName)
        );

        Member saveMember = memberService.save(joinMember);

        return MemberBaseResponseDto.of(saveMember);
    }

    @Override
    public MemberBaseResponseDto login(String roleName, AuthLoginRequestDto requestDto, HttpServletRequest request) {

        Member findMember = memberService.findByEmail(requestDto.getEmail());

        matchesRole(roleName, findMember.getRole().name());

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
    public MemberBaseResponseDto logout(String roleName, Long memberId, HttpServletRequest httpServletRequest) {

        Member findMember = memberService.findById(memberId);

        matchesRole(roleName, findMember.getRole().name());

        sessionService.invalidate(httpServletRequest);

        return MemberBaseResponseDto.of(memberId);
    }

    private void matchesRole(String requestRoleName, String memberRoleName) {
        if (requestRoleName.equals(memberRoleName)) {
            throw MISMATCH_ROLE;
        }
    }
}
