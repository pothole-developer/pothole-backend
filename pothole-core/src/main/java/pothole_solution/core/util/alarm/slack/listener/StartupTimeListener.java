package pothole_solution.core.util.alarm.slack.listener;

import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import pothole_solution.core.util.alarm.slack.SlackService;

import java.time.Duration;

import static pothole_solution.core.util.alarm.slack.constant.SlackConstant.MANAGER_SERVER;
import static pothole_solution.core.util.alarm.slack.constant.SlackConstant.WORKER_SERVER;

public class StartupTimeListener implements SpringApplicationRunListener {
    @Override
    public void ready(ConfigurableApplicationContext context, Duration timeTaken) {
        SpringApplicationRunListener.super.ready(context, timeTaken);

        SlackService slackService = context.getBean(SlackService.class);

        long millis = timeTaken.toMillis();

        String startupTime = String.valueOf(millis / 1000.0);

        boolean serverNameCheck = context.getEnvironment().matchesProfiles("manager-dev");

        if (serverNameCheck) {
            slackService.sendSlackMessage(MANAGER_SERVER, startupTime, true);

        } else {
            slackService.sendSlackMessage(WORKER_SERVER, startupTime, true);
        }
    }
}
