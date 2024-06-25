package pothole_solution.core.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import pothole_solution.core.domain.member.entity.Member;

@Getter
@Builder
public class MemberBaseResponseDto {

    private Long memberId;

    public static MemberBaseResponseDto of(Member member) {
        return MemberBaseResponseDto.builder()
                .memberId(member.getId())
                .build();
    }

    public static MemberBaseResponseDto of(Long memberId) {
        return MemberBaseResponseDto.builder()
                .memberId(memberId)
                .build();
    }
}
