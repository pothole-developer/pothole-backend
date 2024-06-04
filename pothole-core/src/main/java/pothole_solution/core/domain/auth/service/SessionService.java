package pothole_solution.core.domain.auth.service;

import jakarta.servlet.http.HttpServletRequest;

public interface SessionService {
    void create(String email, String role, HttpServletRequest httpServletRequest);
    void invalidate(HttpServletRequest httpServletRequest);
    String getAttribute(String sessionUserId, HttpServletRequest httpServletRequest);
}
