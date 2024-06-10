package pothole_solution.manager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pothole_solution.core.domain.pothole.entity.Pothole;
import pothole_solution.core.domain.pothole.entity.Progress;
import pothole_solution.core.domain.pothole.dto.PotholeFilterDto;
import pothole_solution.core.domain.pothole.dto.request.PotholeChangeProcessStatusRequestDto;
import pothole_solution.core.domain.pothole.dto.request.PotholeRegisterRequestDto;
import pothole_solution.core.domain.pothole.dto.response.SimpleInfoPotholeResponseDto;
import pothole_solution.core.global.util.response.BaseResponse;
import pothole_solution.manager.service.PotholeManagerService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pothole/v1/manager")
public class PotholeManagerController {
    private final PotholeManagerService potholeManagerService;

    @PostMapping
    public BaseResponse<SimpleInfoPotholeResponseDto> register(@RequestPart(value = "registerRequestDto") PotholeRegisterRequestDto registerRequestDto,
                                                               @RequestPart(value = "thumbnail") MultipartFile thumbnail){

        Pothole pothole = potholeManagerService.register(registerRequestDto.toPothole(), thumbnail);

        return new BaseResponse<>(new SimpleInfoPotholeResponseDto(pothole));
    }

    @GetMapping("/{id}")
    public BaseResponse<SimpleInfoPotholeResponseDto> getPothole(@PathVariable("id") Long id) {
        Pothole pothole = potholeManagerService.getPothole(id);

        return new BaseResponse<>(new SimpleInfoPotholeResponseDto(pothole));
    }

    @GetMapping("/potholes")
    public BaseResponse<List<SimpleInfoPotholeResponseDto>> getAllPotholes() {
        List<Pothole> potholes = potholeManagerService.getAllPotholes();

        return new BaseResponse<>(potholes.stream()
                .map(SimpleInfoPotholeResponseDto::new)
                .toList());
    }

    @PutMapping("/{id}")
    public BaseResponse<SimpleInfoPotholeResponseDto> changePotholeSimpleInfo(@PathVariable("id") Long id,
                                                                            @RequestPart(value = "changeProgressRequestDto", required = false) PotholeChangeProcessStatusRequestDto changeProgressRequestDto,
                                                                            @RequestPart(value = "newThumbnail", required = false) MultipartFile newThumbnail) {

        Pothole pothole = potholeManagerService.changePotholeSimpleInfo(id, changeProgressRequestDto, newThumbnail);

        return new BaseResponse<>(new SimpleInfoPotholeResponseDto(pothole));
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deletePothole(@PathVariable("id") Long id) {
        potholeManagerService.deletePothole(id);

        return new BaseResponse<>();
    }

    @GetMapping("/potholes-filters")
    public BaseResponse<List<SimpleInfoPotholeResponseDto>> getFilteredPotholes(@RequestParam(value = "minImportance", required = false) Integer minImportance,
                                                                                @RequestParam(value = "maxImportance", required = false) Integer maxImportance,
                                                                                @RequestParam(value = "process", required = false) Progress processStatus) {

        List<Pothole> filteredPotholes = potholeManagerService.getFilteredPotholes(new PotholeFilterDto(minImportance, maxImportance, processStatus));

        return new BaseResponse<>(filteredPotholes.stream()
                .map(SimpleInfoPotholeResponseDto::new)
                .toList());
    }
}
