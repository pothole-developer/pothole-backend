package pothole_solution.core.util.alarm.slack.listener;

import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;
import pothole_solution.core.util.alarm.slack.SlackService;

import java.io.IOException;

import static pothole_solution.core.util.alarm.slack.constant.SlackConstant.*;

public class StartupFailureListener implements ApplicationListener<ApplicationFailedEvent> {
    @Override
    public void onApplicationEvent(ApplicationFailedEvent event) {
        try {
            // 환경 설정 로드
            ConfigurableEnvironment environment = new StandardEnvironment();
            environment.getPropertySources().addLast(new ResourcePropertySource("classpath:application-core-dev.yml"));

            // SlackService 객체 수동 생성 및 초기화
            String slackToken = environment.getProperty("slack.token");

            SlackService slackService = new SlackService();
            slackService.setSlackToken(slackToken);

            boolean serverNameCheck = new ClassPathResource("application-manager-dev.yml").exists();

            if (serverNameCheck) {
                slackService.sendSlackMessage(MANAGER_SERVER, FAILURE_STARTUP_TIME, false);

            } else {
                slackService.sendSlackMessage(WORKER_SERVER, FAILURE_STARTUP_TIME, false);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
