package pothole_solution.manager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pothole_solution.core.domain.pothole.dto.response.RespPotHistImgGetPotHistImgMngrCntrDto;
import pothole_solution.core.domain.pothole.entity.PotholeHistoryImage;
import pothole_solution.core.global.util.response.BaseResponse;
import pothole_solution.manager.service.PotholeHistoryImageManagerService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pothole/v1/manager/pothole-history-image")
public class PotholeHistoryImageManagerController {
    private final PotholeHistoryImageManagerService potholeHistoryImageManagerService;

    @PostMapping("/{potholeHistoryId}")
    public BaseResponse<List<RespPotHistImgGetPotHistImgMngrCntrDto>> registerPotholeHistoryImage(@PathVariable("potholeHistoryId") Long potholeHistoryId,
                                                                                                  @RequestPart List<MultipartFile> potholeHistoryImages) {

        List<PotholeHistoryImage> potholeHistoryImageList = potholeHistoryImageManagerService.registerPotholeHistoryImage(potholeHistoryId, potholeHistoryImages);

        return new BaseResponse<>(potholeHistoryImageList.stream()
                                                               .map(RespPotHistImgGetPotHistImgMngrCntrDto::new)
                                                               .toList());
    }

    @GetMapping("/{potholeHistoryImageId}")
    public BaseResponse<RespPotHistImgGetPotHistImgMngrCntrDto> getPotholeHistoryImage(@PathVariable("potholeHistoryImageId") Long potholeHistoryImageId) {
        PotholeHistoryImage potholeHistoryImage = potholeHistoryImageManagerService.getPotholeHistoryImage(potholeHistoryImageId);

        return new BaseResponse<>(new RespPotHistImgGetPotHistImgMngrCntrDto(potholeHistoryImage));
    }

    @DeleteMapping("/{potholeHistoryImageId}")
    public BaseResponse<Void> deletePotholeHistoryImage(@PathVariable("potholeHistoryImageId") Long potholeHistoryImageId) {
        potholeHistoryImageManagerService.deletePotholeHistoryImage(potholeHistoryImageId);

        return new BaseResponse<>();
    }


}
