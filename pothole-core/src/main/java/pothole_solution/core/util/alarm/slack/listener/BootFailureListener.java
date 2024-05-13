package pothole_solution.core.util.alarm.slack.listener;

import com.slack.api.model.block.LayoutBlock;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;
import pothole_solution.core.util.alarm.slack.SlackMessageFormatter;
import pothole_solution.core.util.alarm.slack.SlackService;

import java.io.IOException;
import java.util.List;

import static pothole_solution.core.util.alarm.slack.constant.SlackConstant.*;

public class BootFailureListener implements ApplicationListener<ApplicationFailedEvent> {
    @Override
    public void onApplicationEvent(@NotNull ApplicationFailedEvent event) {
        try {
            SlackService slackService = new SlackService(getSlackToken());

            boolean isManagerServer = new ClassPathResource("application-manager-dev.yml").exists();

            String serverName = isManagerServer ? MANAGER_SERVER : WORKER_SERVER;

            List<LayoutBlock> layoutBlocks = new SlackMessageFormatter().buildBootMessageFormat(serverName, FAILURE_STARTUP_TIME, false);
            slackService.sendMessage(POTHOLE_SERVER_DEPLOY, POTHOLE_SERVER_DEPLOY_PREVIEW_MSG, layoutBlocks);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getSlackToken() throws IOException {
        // 환경 설정 로드
        ConfigurableEnvironment environment = new StandardEnvironment();
        environment.getPropertySources().addLast(new ResourcePropertySource("classpath:application-core-dev.yml"));

        return environment.getProperty("slack.token");
    }
}
