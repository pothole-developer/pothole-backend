package pothole_solution.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// 스프링 부트가 JPA 엔티티(@Entity로 선언된 클래스)를 스캔할 위치를 지정
// 멀티 모듈 프로젝트에서 엔티티 클래스가 메인 모듈이 아닌 다른 모듈에 위치한 경우 스프링 부트의 기본 엔티티 스캔 범위(메인 애플리케이션 클래스가 속한 패키지와 그 하위 패키지)를 벗어나므로
// @EntityScan을 사용하여 엔티티 클래스가 위치한 패키지를 수동으로 지정해 주어야 한다.
@EntityScan("pothole_solution.core")

// 스프링 데이터 JPA 리포지토리 인터페이스가 위치한 패키지를 지정합니다. 이 어노테이션을 사용하면, 스프링 부트는 지정된 패키지 경로 내에서 리포지토리 인터페이스를 찾아 스프링 데이터 JPA 리포지토리로 등록하고, 자동으로 구현체를 생성한다.
// 멀티 모듈 프로젝트에서 리포지토리 인터페이스가 메인 모듈이 아닌 다른 모듈에 위치해 있을 경우, 스프링 부트의 기본 리포지토리 스캔 범위를 벗어나게 된다.
// 따라서, @EnableJpaRepositories를 사용하여 리포지토리 인터페이스가 위치한 패키지를 수동으로 지정해 주어야 한다.
@EnableJpaRepositories("pothole_solution.core")

// scanBasePackages 속성을 사용하면, 스프링 부트가 컴포넌트(@Component, @Service, @Repository 등)를 스캔할 패키지 경로를 지정할 수 있다.
// 멀티 모듈 프로젝트에서 서비스, 컨트롤러 등의 스프링 컴포넌트가 메인 모듈이 아닌 다른 모듈에 위치해 있을 경우, 스프링 부트의 기본 컴포넌트 스캔 범위를 벗어나게 된다.
// 따라서, `scanBasePackages` 속성을 사용하여 컴포넌트가 위치한 패키지를 수동으로 지정해 주어야 한다. 이를 통해 스프링 부트가 멀티 모듈 구조에서도 필요한 컴포넌트들을 정확하게 인식하고 빈으로 등록할 수 있게 된다.
@SpringBootApplication(scanBasePackages = {"pothole_solution.core", "pothole_solution.worker"})
public class WorkerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkerApplication.class, args);
    }
}
