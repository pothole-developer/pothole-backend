package pothole_solution.core.global.util.alarm.slack.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static pothole_solution.core.global.util.alarm.slack.constant.SlackConstant.NO_PR_INFO_MSG;

@NoArgsConstructor
@Getter
public class SlkPrInfoSlkMsgFmtrDto {
    private String author;
    private String title;
    private String url;

    @Builder
    public SlkPrInfoSlkMsgFmtrDto(String author, String title, String url) {
        this.author = author;
        this.title = title;
        this.url = url;
    }

    public void changeNoAuthorValue() {
        this.author = NO_PR_INFO_MSG;
    }

    public void changeNoTitleValue() {
        this.title = NO_PR_INFO_MSG;
    }
}
