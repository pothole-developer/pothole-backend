package pothole_solution.manager.sample.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pothole_solution.core.PointDto;
import pothole_solution.core.Pothole;
import pothole_solution.core.Progress;
import pothole_solution.manager.sample.service.SampleService;
import pothole_solution.core.util.alarm.slack.SlackService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pothole/v1/manager")
@Slf4j
public class SampleManagerController {
    private final SampleService sampleService;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    private final SlackService slackService;

    @PostMapping
    public ResponseEntity<Long> register(){
        Pothole sample = Pothole.builder().location(geometryFactory.createPoint(new Coordinate(126.93427307071744, 37.38609685274056))).progress(Progress.READY).build();
        Pothole pothole = sampleService.register(sample);

        return ResponseEntity.ok().body(pothole.getPothole_id());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PointDto> getPothole(@PathVariable("id") Long id) {
        Pothole pothole = sampleService.getPothole(id);

        return ResponseEntity.ok().body(new PointDto(pothole));
    }

    @GetMapping("/potholes")
    public ResponseEntity<List<PointDto>> getAllPotholes() {
        log.info("Slack Message 테스트");

        slackService.sendSlackMessage("포트홀 전체 조회 시작");

        List<Pothole> potholes = sampleService.getAllPotholes();

        slackService.sendSlackMessage("포트홀 전체 조회 끝");

        return ResponseEntity.ok().body(potholes.stream()
                .map(PointDto::new)
                .toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PointDto> changePothole(@PathVariable("id") Long id) {
        Pothole pothole = sampleService.changePotholeProgress(id);

        return ResponseEntity.ok().body(new PointDto(pothole));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePothole(@PathVariable("id") Long id) {
        sampleService.deletePothole(id);

        return ResponseEntity.ok().build();
    }
}
