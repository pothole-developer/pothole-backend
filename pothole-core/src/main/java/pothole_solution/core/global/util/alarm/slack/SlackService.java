package pothole_solution.core.global.util.alarm.slack;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.model.block.LayoutBlock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class SlackService {
    private final String slackToken;

    public SlackService(@Value(value = "${slack.token}") String slackToken) {
        this.slackToken = slackToken;
    }

    public void sendMessage(String channel, String preview, List<LayoutBlock> messages) {
        try {
            MethodsClient methodsClient = Slack.getInstance().methods(slackToken);

            ChatPostMessageRequest messageRequest = ChatPostMessageRequest.builder()
                    .channel(channel)
                    .text(preview)
                    .blocks(messages)
                    .build();

            methodsClient.chatPostMessage(messageRequest);

        } catch (SlackApiException | IOException e) {
            log.error("slack error: {}", e.getMessage(), e);
        }
    }
}
