package pothole_solution.manager.sample.controller;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pothole_solution.core.pothole.Pothole;
import pothole_solution.core.pothole.PotholeDto;
import pothole_solution.core.pothole.Progress;
import pothole_solution.manager.sample.service.SampleService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pothole/v1/manager")
public class SampleManagerController {
    private final SampleService sampleService;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    @PostMapping
    public ResponseEntity<Long> register(){
        Pothole sample = Pothole.builder().location(geometryFactory.createPoint(new Coordinate(126.93427307071744, 37.38609685274056))).progress(Progress.REGISTER).build();
        Pothole pothole = sampleService.register(sample);

        return ResponseEntity.ok().body(pothole.getPotholeId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PotholeDto> getPothole(@PathVariable("id") Long id) {
        Pothole pothole = sampleService.getPothole(id);

        return ResponseEntity.ok().body(new PotholeDto(pothole));
    }

    @GetMapping("/potholes")
    public ResponseEntity<List<PotholeDto>> getAllPotholes() {
        List<Pothole> potholes = sampleService.getAllPotholes();

        return ResponseEntity.ok().body(potholes.stream()
                .map(PotholeDto::new)
                .toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PotholeDto> changePothole(@PathVariable("id") Long id) {
        Pothole pothole = sampleService.changePotholeProgress(id);

        return ResponseEntity.ok().body(new PotholeDto(pothole));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePothole(@PathVariable("id") Long id) {
        sampleService.deletePothole(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/potholes-filters")
    public ResponseEntity<List<PotholeDto>> getFilteredPotholes(@RequestParam(value = "minImportance", required = false) Integer minImportance, @RequestParam(value = "maxImportance", required = false) Integer maxImportance, @RequestParam(value = "process", required = false) String process) {
        List<Pothole> filteredPotholes = sampleService.getFilteredPotholes(minImportance, maxImportance, process);

        return ResponseEntity.ok().body(filteredPotholes.stream()
                .map(PotholeDto::new)
                .toList());
    }
}
