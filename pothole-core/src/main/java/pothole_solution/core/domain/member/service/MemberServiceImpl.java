package pothole_solution.core.domain.member.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pothole_solution.core.domain.member.dto.MemberBaseResponseDto;
import pothole_solution.core.domain.member.dto.MemberJoinRequestDto;
import pothole_solution.core.domain.member.dto.MemberLoginRequestDto;
import pothole_solution.core.domain.member.entity.Member;
import pothole_solution.core.domain.member.entity.Role;
import pothole_solution.core.domain.member.repository.MemberRepository;
import pothole_solution.core.util.convertor.SessionConstant;
import pothole_solution.core.util.exception.CustomException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    @NotNull
    @Override
    public Member save(Member joinMember) {
        return memberRepository.save(joinMember);
    }

    @Override
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> CustomException.NONE_USER);
    }

    @Override
    public boolean existsByEmail(String email) {
        return memberRepository.findByEmail(email)
                .isPresent();
    }

    @Override
    public void validateDuplicateEmail(String email) {
        if (existsByEmail(email)){
            throw CustomException.DUPLICATED_EMAIL;
        }
    }

    @Override
    public MemberBaseResponseDto join(MemberJoinRequestDto requestDto) {

        validateDuplicateEmail(requestDto.getEmail());

        Member joinMember = Member.create(requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPassword(),
                requestDto.getContact(),
                Role.valueOf(requestDto.getRole()));

        Member saveMember = save(joinMember);

        return MemberBaseResponseDto.of(saveMember);
    }

    @Override
    public MemberBaseResponseDto login(MemberLoginRequestDto requestDto,
                                       HttpServletRequest httpServletRequest) {

        Member findMember = findByEmail(requestDto.getEmail());

        matchesPassword(requestDto.getPassword(), findMember.getPassword());

        // TODO: SessionService 로 역할 분리
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(SessionConstant.MANAGER_SESSION_KEY, findMember.getId());

        return MemberBaseResponseDto.of(findMember);
    }

    private void matchesPassword(String rawPassword, String encodedPassword) {
        if (!encodedPassword.equals(rawPassword)) {
            throw CustomException.INVALID_PARAMETER;
        }
    }

    @Override
    public String logout(HttpServletRequest httpServletRequest) {

        HttpSession session = httpServletRequest.getSession();
        session.invalidate();

        return "로그아웃 성공";
    }
}