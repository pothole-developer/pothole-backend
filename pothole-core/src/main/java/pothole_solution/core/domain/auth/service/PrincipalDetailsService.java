package pothole_solution.core.domain.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pothole_solution.core.domain.member.entity.Member;
import pothole_solution.core.domain.auth.entity.PrincipalDetails;
import pothole_solution.core.domain.member.repository.MemberRepository;
import pothole_solution.core.util.exception.CustomException;
import pothole_solution.core.util.exception.ExceptionStatus;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public PrincipalDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ExceptionStatus.NONE_USER));

        log.info("loadUserByUsername, member=[{}][{}][{}]", member.getId(),member.getEmail(), member.getRole());
        return new PrincipalDetails(member.getId(), member.getName(), member.getRole().getRoleName());
    }
}
