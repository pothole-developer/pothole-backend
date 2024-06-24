package pothole_solution.core.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pothole_solution.core.util.exception.CustomException;

@Getter
@AllArgsConstructor
public enum Role {
    MANAGER("ROLE_MANAGER"),
    GUEST("ROLE_GUEST"),
    WORKER("ROLE_WORKER");

    private final String roleName;

    public static Role from(String roleName){
        try {
            return Role.valueOf(roleName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw CustomException.NONE_ROLE;
        }
    }
}
