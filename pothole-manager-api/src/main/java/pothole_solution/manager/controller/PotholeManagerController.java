package pothole_solution.manager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pothole_solution.core.domain.pothole.dto.PotholeFilterDto;
import pothole_solution.core.domain.pothole.dto.request.ChangePotholeProgressStatusRequestDto;
import pothole_solution.core.domain.pothole.dto.request.RegisterPotholeRequestDto;
import pothole_solution.core.domain.pothole.dto.response.DetailsInfoPotholeResponseDto;
import pothole_solution.core.domain.pothole.dto.response.GetPotholeHistoryResponseDto;
import pothole_solution.core.domain.pothole.dto.response.SimpleInfoPotholeResponseDto;
import pothole_solution.core.domain.pothole.entity.Pothole;
import pothole_solution.core.domain.pothole.entity.PotholeHistory;
import pothole_solution.core.domain.pothole.entity.PotholeHistoryImage;
import pothole_solution.core.domain.pothole.entity.Progress;
import pothole_solution.core.global.util.response.BaseResponse;
import pothole_solution.manager.service.PotholeHistoryImageManagerService;
import pothole_solution.manager.service.PotholeManagerService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pothole/v1/manager")
public class PotholeManagerController {
    private final PotholeManagerService potholeManagerService;
    private final PotholeHistoryImageManagerService potholeHistoryImageManagerService;

    @PostMapping
    public BaseResponse<SimpleInfoPotholeResponseDto> registerPothole(@Valid @RequestPart(value = "registerPotholeRequestDto") RegisterPotholeRequestDto registerPotholeRequestDto,
                                                                      @RequestPart(value = "registerPotholeImages") List<MultipartFile> registerPotholeImages){

        Pothole pothole = potholeManagerService.registerPothole(registerPotholeRequestDto.toPothole(), registerPotholeImages);

        return new BaseResponse<>(new SimpleInfoPotholeResponseDto(pothole));
    }

    @GetMapping("/{potholeId}")
    public BaseResponse<DetailsInfoPotholeResponseDto> getPotholeDetails(@PathVariable("potholeId") Long potholeId) {
        Pothole pothole = potholeManagerService.getPotholeWithPotholeHistoryByPotholeId(potholeId);
        List<PotholeHistory> potholeHistories = pothole.getPotholeHistories();
        List<PotholeHistoryImage> potholeHistoryImages = potholeHistoryImageManagerService.getAllPotholeHistoryImageByPotholeHistoryId(potholeHistories);

        return new BaseResponse<>(new DetailsInfoPotholeResponseDto(pothole, getPotholeHistoryResponseDtoList(potholeHistories, potholeHistoryImages)));
    }

    private List<GetPotholeHistoryResponseDto> getPotholeHistoryResponseDtoList(List<PotholeHistory> potholeHistories, List<PotholeHistoryImage> potholeHistoryImages) {
        return potholeHistories.stream()
                .map(potholeHistory -> new GetPotholeHistoryResponseDto(
                        potholeHistory,
                        getPotholeHistoryImagesByPotholeHistory(potholeHistoryImages, potholeHistory.getPotholeHistoryId())
                ))
                .toList();
    }

    private List<PotholeHistoryImage> getPotholeHistoryImagesByPotholeHistory(List<PotholeHistoryImage> potholeHistoryImages, Long potholeHistoryId) {
        return potholeHistoryImages.stream()
                .filter(potholeHistoryImage -> potholeHistoryImage.getPotholeHistory().getPotholeHistoryId().equals(potholeHistoryId))
                .toList();
    }

    @GetMapping("/potholes")
    public BaseResponse<List<SimpleInfoPotholeResponseDto>> getAllPotholes() {
        List<Pothole> potholes = potholeManagerService.getAllPotholes();

        return new BaseResponse<>(potholes.stream()
                                          .map(SimpleInfoPotholeResponseDto::new)
                                          .toList()
        );
    }

    @PutMapping("/{potholeId}")
    public BaseResponse<SimpleInfoPotholeResponseDto> changePotholeProgressStatus(@PathVariable("potholeId") Long potholeId,
                                                                                  @Valid @RequestBody ChangePotholeProgressStatusRequestDto changeProgressStatusRequestDto) {

        Pothole pothole = potholeManagerService.changePotholeProgressStatus(potholeId, changeProgressStatusRequestDto);

        return new BaseResponse<>(new SimpleInfoPotholeResponseDto(pothole));
    }

    @DeleteMapping("/{potholeId}")
    public BaseResponse<Void> deletePothole(@PathVariable("potholeId") Long potholeId) {
        potholeManagerService.deletePothole(potholeId);

        return new BaseResponse<>();
    }

    @GetMapping("/potholes-filters")
    public BaseResponse<List<SimpleInfoPotholeResponseDto>> getFilteredPotholes(@RequestParam(value = "minImportance", required = false) Integer minImportance,
                                                                                @RequestParam(value = "maxImportance", required = false) Integer maxImportance,
                                                                                @RequestParam(value = "potholeProgressStatus", required = false) Progress potholeProgressStatus) {

        List<Pothole> filteredPotholes = potholeManagerService.getFilteredPotholes(new PotholeFilterDto(minImportance, maxImportance, potholeProgressStatus));

        return new BaseResponse<>(filteredPotholes.stream()
                                                  .map(SimpleInfoPotholeResponseDto::new)
                                                  .toList()
        );
    }
}
