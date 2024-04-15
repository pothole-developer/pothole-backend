package pothole_solution.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("pothole_solution.core")
@EnableJpaRepositories("pothole_solution.core")
@SpringBootApplication(scanBasePackages = {"pothole_solution.core", "pothole_solution.worker"})
public class WorkerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkerApplication.class, args);
    }
}
