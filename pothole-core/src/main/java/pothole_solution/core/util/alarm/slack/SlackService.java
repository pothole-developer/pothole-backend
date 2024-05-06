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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static pothole_solution.core.util.alarm.slack.constant.SlackConstant.NO_PR_INFO_MSG;

@Slf4j
@Service
public class SlackService {
    @Value(value = "${slack.token}")
    String slackToken;

    public void sendSlackMessage(String serverName, String startupTime, boolean status) {
        try {
            MethodsClient methodsClient = Slack.getInstance().methods(slackToken);

            List<LayoutBlock> slackMsg = getSlackMessage(serverName, startupTime, status);

            ChatPostMessageRequest messageRequest = ChatPostMessageRequest.builder()
                    .channel(SlackConstant.POTHOLE_SERVER_DEPLOY)
                    .text(":loudspeaker:  포트홀 서버가 배포되었습니다. 배포된 내용을 확인해주세요.") // 슬랙 메시지 수신 시 미리보기 메시지
                    .blocks(slackMsg)
                    .build();

            methodsClient.chatPostMessage(messageRequest);

        } catch (SlackApiException | IOException e) {
            log.error("slack error: {}", e.getMessage(), e);
        }
    }

    @NotNull
    private List<LayoutBlock> getSlackMessage(String serverName, String startupTime, boolean status) {
        String statusEmoji;

        // 애플리케이션 실행 상태 확인
        if (status) {
            statusEmoji = ":white_check_mark:";

        } else {
            statusEmoji = ":x:";
        }

        String prAuthor = getPRInfo("PR Author: ");
        String prTitle = getPRInfo("PR Title: ");
        String prUrl = getPRInfo("PR URL: ");

        List<LayoutBlock> layoutBlocks = new ArrayList<>();

        leaveSpace(layoutBlocks);

        // header
        layoutBlocks.add(header(headerBlockBuilder -> headerBlockBuilder.text(plainText(":newspaper:  포트홀 서버 배포 알림  :newspaper:"))));
        layoutBlocks.add(divider());

        // title
        layoutBlocks.add(section(section -> section.text(markdownText(":placard:  *Title*"))));
        layoutBlocks.add(section(section -> section.text(markdownText(prTitle))));
        layoutBlocks.add(divider());

        // server name
        layoutBlocks.add(section(section -> section.text(markdownText(":open_file_folder:  *배포 서버*"))));
        layoutBlocks.add(section(section -> section.text(markdownText("*" + serverName +"*"))));
        layoutBlocks.add(divider());

        // other info
        layoutBlocks.add(section(section -> section.text(markdownText(":mag_right:  *기타 정보*"))));
        layoutBlocks.add(section(section -> section.text(markdownText("*배포 성공 여부*  |  " + statusEmoji))));
        layoutBlocks.add(section(section -> section.text(markdownText("*배포 소요 시간 (단위: 초)*  |  `" + startupTime + "`"))));
        layoutBlocks.add(section(section -> section.text(markdownText("*PR 생성자*  |  " + prAuthor))));
        layoutBlocks.add(section(section -> section.text(markdownText("*PR URL*  |  <" + prUrl + "|PR 보기>"))));

        leaveSpace(layoutBlocks);
        leaveSpace(layoutBlocks);

        return layoutBlocks;
    }

    private void leaveSpace(List<LayoutBlock> layoutBlocks) {
        layoutBlocks.add(section(section -> section.text(markdownText(" "))));
    }

    @NotNull
    private String getPRInfo(String info) {
        try {
            return Files.readAllLines(Paths.get("./pothole-core/src/main/resources/pr_info.txt")).stream()
                    .filter(prInfo -> prInfo.startsWith(info))
                    .findFirst()
                    .map(prInfo -> prInfo.substring(info.length()).trim())
                    .orElse(NO_PR_INFO_MSG);

        } catch (IOException e) {
            e.printStackTrace();

            return NO_PR_INFO_MSG;
        }
    }
}
