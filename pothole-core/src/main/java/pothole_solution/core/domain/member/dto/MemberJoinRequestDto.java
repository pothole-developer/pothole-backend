package pothole_solution.core.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberJoinRequestDto {
    @NotBlank(message = "이름 입력은 필수 입니다.")
    private String name;

    @NotBlank(message = "이메일 입력은 필수 입니다.")
    @Email
    private String email;

    @NotBlank(message = "비밀번호 입력은 필수 입니다.")
    private String password;

    private String contact;

    @NotBlank(message = "권한 입력은 필수 입니다.")
    // annotation
    private String role;
}
