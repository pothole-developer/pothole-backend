package pothole_solution.core.infra.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import pothole_solution.core.global.exception.ExceptionStatus;
import pothole_solution.core.global.util.response.BaseResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static pothole_solution.core.global.exception.ExceptionStatus.FORBIDDEN_SESSION;


@Component
@Slf4j
public class SessionAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, jakarta.servlet.ServletException {
        log.info("== JwtAccessDeniedHandler(403 Error) ==");
        BaseResponse<ExceptionStatus> baseResponse = new BaseResponse<>(FORBIDDEN_SESSION);
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), baseResponse);
    }
}
