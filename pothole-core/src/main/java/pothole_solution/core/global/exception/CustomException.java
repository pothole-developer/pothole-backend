package pothole_solution.core.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomException extends RuntimeException {
    private final ExceptionStatus status;

    // common exception
    public static final CustomException INVALID_PARAMETER       = new CustomException(ExceptionStatus.INVALID_PARAMETER);
    public static final CustomException INVALID_URL             = new CustomException(ExceptionStatus.INVALID_URL);
    public static final CustomException INTERNAL_SERVER_ERROR   = new CustomException(ExceptionStatus.INTERNAL_SERVER_ERROR);
    public static final CustomException NOT_EXISTED_FILE        = new CustomException(ExceptionStatus.NOT_EXISTED_FILE);

    // session exception
    public static final CustomException UNAUTHORIZED_SESSION    = new CustomException(ExceptionStatus.UNAUTHORIZED_SESSION);
    public static final CustomException EXPIRED_SESSION         = new CustomException(ExceptionStatus.EXPIRED_SESSION);
    public static final CustomException FORBIDDEN_SESSION       = new CustomException(ExceptionStatus.FORBIDDEN_SESSION);

    // user exception
    public static final CustomException DUPLICATED_EMAIL        = new CustomException(ExceptionStatus.DUPLICATED_EMAIL);
    public static final CustomException NONE_USER               = new CustomException(ExceptionStatus.NONE_USER);
    public static final CustomException MISMATCH_PASSWORD       = new CustomException(ExceptionStatus.MISMATCH_PASSWORD);
    public static final CustomException NONE_ROLE               = new CustomException(ExceptionStatus.NONE_ROLE);
    public static final CustomException MISMATCH_ROLE               = new CustomException(ExceptionStatus.MISMATCH_ROLE);

    // s3 exception
    public static final CustomException NOT_EXISTED_POTHOLE         = new CustomException(ExceptionStatus.NOT_EXISTED_POTHOLE);
    public static final CustomException FAILED_UPLOAD               = new CustomException(ExceptionStatus.FAILED_UPLOAD);
    public static final CustomException INVALID_POTHOLE_IMG_URL     = new CustomException(ExceptionStatus.INVALID_POTHOLE_IMG_URL);
    public static final CustomException INVALID_POTHOLE_IMG_NAME    = new CustomException(ExceptionStatus.INVALID_POTHOLE_IMG_NAME);

    // Report Exception
    public static final CustomException MISMATCH_PERIOD               = new CustomException(ExceptionStatus.MISMATCH_PERIOD);
}
