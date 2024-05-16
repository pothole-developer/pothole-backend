package pothole_solution.core.util.alarm.slack;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slack.api.model.block.LayoutBlock;
import org.springframework.core.io.ClassPathResource;
import pothole_solution.core.util.alarm.slack.dto.PrInfoDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static pothole_solution.core.util.alarm.slack.constant.SlackConstant.NO_PR_INFO_MSG;

public class SlackMessageFormatter {
    public List<LayoutBlock> buildBootMessageFormat(String serverName, String startupTime, boolean isSuccess) {
        // 애플리케이션 실행 상태 확인
        String bootStatusEmoji = isSuccess ? ":white_check_mark:" : ":x:";

        String prAuthor = getPRInfo().getAuthor();
        String prTitle = getPRInfo().getTitle();
        String prUrl = getPRInfo().getUrl();

        String prUrlMsg = prUrl.isEmpty() ? NO_PR_INFO_MSG : "<" + prUrl + "|PR 보기>";

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
        layoutBlocks.add(section(section -> section.text(markdownText("*배포 성공 여부*  |  " + bootStatusEmoji))));
        layoutBlocks.add(section(section -> section.text(markdownText("*배포 소요 시간 (단위: 초)*  |  `" + startupTime + "`"))));
        layoutBlocks.add(section(section -> section.text(markdownText("*PR 생성자*  |  " + prAuthor))));
        layoutBlocks.add(section(section -> section.text(markdownText("*PR URL*  |  " + prUrlMsg))));

        leaveSpace(layoutBlocks);
        leaveSpace(layoutBlocks);

        return layoutBlocks;
    }

    private void leaveSpace(List<LayoutBlock> layoutBlocks) {
        layoutBlocks.add(section(section -> section.text(markdownText(" "))));
    }

    private PrInfoDto getPRInfo() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            ClassPathResource prInfoJson = new ClassPathResource("pr_info.json");

            PrInfoDto prInfoDto = objectMapper.readValue(prInfoJson.getInputStream(), PrInfoDto.class);

            if (prInfoDto.getAuthor().isEmpty()) {
                prInfoDto.changeNoAuthorValue();
            }

            if (prInfoDto.getTitle().isEmpty()) {
                prInfoDto.changeNoTitleValue();
            }

            return prInfoDto;

        } catch (IOException e) {
            e.printStackTrace();

            return PrInfoDto.builder()
                    .author(NO_PR_INFO_MSG)
                    .title(NO_PR_INFO_MSG)
                    .url(NO_PR_INFO_MSG)
                    .build();
        }
    }
}
