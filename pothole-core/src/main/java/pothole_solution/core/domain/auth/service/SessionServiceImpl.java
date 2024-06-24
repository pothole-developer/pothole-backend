package pothole_solution.core.domain.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import static pothole_solution.core.domain.auth.entity.SessionConstant.*;

@Component
public class SessionServiceImpl implements SessionService{

    @Override
    public void create(String email, String role, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(SESSION_USER_ID, email);
        session.setAttribute(SESSION_USER_ROLE, role);
    }

    @Override
    public void invalidate(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
    }

    @Override
    public String getAttribute(String key, HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (String) session.getAttribute(key);
    }
}
