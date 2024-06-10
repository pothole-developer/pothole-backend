package pothole_solution.core.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ExceptionStatus {
    // common exception
    INVALID_PARAMETER(BAD_REQUEST, 2000, "잘못된 요청이 존재합니다."),
    INVALID_URL(BAD_REQUEST, 2001, "잘못된 URL 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 2002,"서버 내부 오류입니다."),
    NOT_EXISTED_FILE(NOT_EXTENDED, 2003,"존재하지 않는 파일입니다."),

    // user exception
    DUPLICATED_EMAIL(CONFLICT, 3000, "중복된 이메일이 존재합니다."),
    NONE_USER(NOT_FOUND, 3001, "존재하지 않는 회원입니다."),

    // manager exception
    NOT_EXISTED_POTHOLE(NOT_FOUND, 4000, "존재하지 않는 포트홀입니다."),

    // s3 exception
    FAILED_UPLOAD(NOT_FOUND, 5000, "포트홀 이미지 업로드에 실패했습니다."),
    INVALID_POTHOLE_IMG_URL(BAD_REQUEST, 5001, "잘못된 포트홀 이미지 URL 요청입니다."),
    INVALID_POTHOLE_IMG_NAME(BAD_REQUEST, 5002, "포트홀 이미지의 이름이 존재하지 않거나 잘못되었습니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
