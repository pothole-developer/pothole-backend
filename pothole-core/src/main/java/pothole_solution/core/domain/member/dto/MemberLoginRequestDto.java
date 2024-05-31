package pothole_solution.core.domain.member.dto;

import lombok.Getter;

@Getter
public class MemberLoginRequestDto {
    private String email;
    private String password;
}
