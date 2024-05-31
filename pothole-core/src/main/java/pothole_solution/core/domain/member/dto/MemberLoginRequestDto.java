package pothole_solution.core.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberLoginRequestDto {

    @NotBlank(message = "이메일 입력은 필수 입니다.")
    @Email
    private String email;

    @NotBlank(message = "비밀번호 입력은 필수 입니다.")
    private String password;
}
