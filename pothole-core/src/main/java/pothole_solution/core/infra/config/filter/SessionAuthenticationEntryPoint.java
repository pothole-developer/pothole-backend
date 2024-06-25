package pothole_solution.core.infra.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import pothole_solution.core.global.exception.ExceptionStatus;
import pothole_solution.core.global.util.response.BaseResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static pothole_solution.core.global.exception.ExceptionStatus.UNAUTHORIZED_SESSION;

@Component
@Slf4j
public class SessionAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, AuthenticationException authException) throws IOException, jakarta.servlet.ServletException {
        log.info("== JwtAuthenticationEntryPoint(401 Error) ==");
        BaseResponse<ExceptionStatus> baseResponse = new BaseResponse<>(UNAUTHORIZED_SESSION);
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), baseResponse);
    }
}
