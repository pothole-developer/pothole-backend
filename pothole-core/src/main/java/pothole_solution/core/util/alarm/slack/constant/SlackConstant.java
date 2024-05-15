package pothole_solution.core.util.alarm.slack.constant;

// 추후 채널 추가 시 채널에 따라 다른 작업을 하기 위해 생성
public class SlackConstant {

    // Slack에 생성된 채널명
    public static final String POTHOLE_SERVER_DEPLOY = "#포트홀";

    public static final String MANAGER_SERVER = "Manager";

    public static final String WORKER_SERVER = "Worker";

    public static final String POTHOLE_SERVER_DEPLOY_PREVIEW_MSG = ":loudspeaker:  포트홀 서버가 배포되었습니다. 배포된 내용을 확인해주세요.";

    public static final String FAILURE_STARTUP_TIME = "0.000";

    public static final String NO_PR_INFO_MSG = "N/A";

    public static final String MANAGER_SERVER_PR_INFO_PATH = "./pothole-manager-api/src/main/resources/pr_info.txt";
    public static final String WORKER_SERVER_PR_INFO_PATH = "./pothole-worker-api/src/main/resources/pr_info.txt";
}
