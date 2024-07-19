package pothole_solution.core.domain.pothole.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pothole_solution.core.domain.pothole.entity.PotholeHistoryImage;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RespPotHistImgGetPotHistImgMngrCntrDto {
    private Long potholeHistoryImageId;

    private String potholeHistoryImageUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Builder
    public RespPotHistImgGetPotHistImgMngrCntrDto(PotholeHistoryImage potholeHistoryImg) {
        this.potholeHistoryImageId = potholeHistoryImg.getPotholeHistoryImgId();
        this.potholeHistoryImageUrl = potholeHistoryImg.getImage();
        this.createdAt = potholeHistoryImg.getCreatedAt();
    }
}
