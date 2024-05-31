package pothole_solution.core.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    MANAGER("ROLE_MANAGER"),
    GUEST("ROLE_GUEST"),
    WORKER("ROLE_WORKER");

    private final String roleName;
}
