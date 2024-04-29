package pothole_solution.core.util.alarm.slack;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pothole_solution.core.util.alarm.slack.constant.SlackConstant;

import java.io.IOException;

@Slf4j
@Service
public class SlackService {
    @Value(value = "${slack.token}")
    String slackToken;

    public void sendSlackMessage(String message) {
        try {
            MethodsClient methodsClient = Slack.getInstance().methods(slackToken);

            ChatPostMessageRequest messageRequest = ChatPostMessageRequest.builder()
                    .channel(SlackConstant.POTHOLE_SERVER_DEPLOY)
                    .text(message)
                    .build();

            methodsClient.chatPostMessage(messageRequest);

            log.info("Slack " + SlackConstant.POTHOLE_SERVER_DEPLOY + "에 메시지 보냄.");

        } catch (SlackApiException | IOException e) {
            log.error("slack error: {}", e.getMessage(), e);
        }
    }
}
