package pothole_solution.worker.sample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pothole/v1/worker")
public class SampleWorkerController {
    @GetMapping("/test")
    public String test() {

        return "Hello Worker";
    }
}
