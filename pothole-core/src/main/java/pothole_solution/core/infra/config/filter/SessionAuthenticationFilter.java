package pothole_solution.core.infra.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pothole_solution.core.domain.auth.entity.PrincipalDetails;
import pothole_solution.core.domain.auth.service.PrincipalDetailsService;
import pothole_solution.core.domain.auth.service.SessionService;

import java.io.IOException;

import static pothole_solution.core.domain.auth.entity.SessionConstant.SESSION_USER_ID;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionAuthenticationFilter extends OncePerRequestFilter {
    private final SessionService sessionService;
    private final PrincipalDetailsService principalDetailsService;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {

        String email = sessionService.getAttribute(SESSION_USER_ID, request);

        if (email != null) {
            Authentication authentication = getAuthentication(email);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private Authentication getAuthentication(String email) {
        PrincipalDetails principalDetails = principalDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(principalDetails, "", principalDetails.getAuthorities());
    }
}
