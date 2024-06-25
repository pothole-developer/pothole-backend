package pothole_solution.core.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pothole_solution.core.domain.member.entity.Member;
import pothole_solution.core.domain.member.repository.MemberRepository;
import pothole_solution.core.global.exception.CustomException;

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
}