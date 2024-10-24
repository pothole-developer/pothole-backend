package pothole_solution.core.domain.member.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import pothole_solution.core.domain.BaseTimeEntity;
import pothole_solution.core.domain.Status;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Size(max = 50)
    @Column(length = 50)
    private String name;

    private String email;

    private String password;

    private String contact;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    private Member(@NotNull String name, @NotNull String email, @NotNull String password, String contact,
                   @NotNull Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.contact = contact;
        this.role = role;
        this.status = Status.ACTIVE;
    }

    public static Member create(String name, String email, String password, String contact, Role role) {
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .contact(contact)
                .role(role)
                .build();
    }
}
