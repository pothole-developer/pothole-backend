package pothole_solution.manager.sample.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pothole_solution.core.pothole.Pothole;
import pothole_solution.core.pothole.dto.request.PotholeChangeProgressRequestDto;
import pothole_solution.core.pothole.dto.request.PotholeRegisterRequestDto;
import pothole_solution.core.pothole.dto.response.PotholeResponseDto;
import pothole_solution.manager.sample.service.SampleService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pothole/v1/manager")
public class SampleManagerController {
    private final SampleService sampleService;

    @PostMapping
    public ResponseEntity<Long> register(@RequestBody PotholeRegisterRequestDto registerRequestDto){
        Pothole pothole = sampleService.register(registerRequestDto.toPothole());

        return ResponseEntity.ok().body(pothole.getPotholeId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PotholeResponseDto> getPothole(@PathVariable("id") Long id) {
        Pothole pothole = sampleService.getPothole(id);

        return ResponseEntity.ok().body(new PotholeResponseDto(pothole));
    }

    @GetMapping("/potholes")
    public ResponseEntity<List<PotholeResponseDto>> getAllPotholes() {
        List<Pothole> potholes = sampleService.getAllPotholes();

        return ResponseEntity.ok().body(potholes.stream()
                .map(PotholeResponseDto::new)
                .toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PotholeResponseDto> changePotholeProgress(@PathVariable("id") Long id, @RequestBody PotholeChangeProgressRequestDto changeProgressRequestDto) {
        Pothole pothole = sampleService.changePotholeProgress(id, changeProgressRequestDto);

        return ResponseEntity.ok().body(new PotholeResponseDto(pothole));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePothole(@PathVariable("id") Long id) {
        sampleService.deletePothole(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/potholes-filters")
    public ResponseEntity<List<PotholeResponseDto>> getFilteredPotholes(@RequestParam(value = "minImportance", required = false) Integer minImportance, @RequestParam(value = "maxImportance", required = false) Integer maxImportance, @RequestParam(value = "process", required = false) String process) {
        List<Pothole> filteredPotholes = sampleService.getFilteredPotholes(minImportance, maxImportance, process);

        return ResponseEntity.ok().body(filteredPotholes.stream()
                .map(PotholeResponseDto::new)
                .toList());
    }
}
