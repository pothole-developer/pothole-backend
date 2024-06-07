package pothole_solution.core.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthJoinRequestDto {
    @NotBlank(message = "이름 입력은 필수 입니다.")
    @Size(max = 50, message = "이름의 길이는 50글자 이하입니다.")
    private String name;

    @NotBlank(message = "이메일 입력은 필수 입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호 입력은 필수 입니다.")
    private String password;

    private String contact;

    @NotBlank(message = "권한 입력은 필수 입니다.")
    private String role;
}
