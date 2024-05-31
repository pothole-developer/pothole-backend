package pothole_solution.core.util.exception;

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

    // user exception
    public static final CustomException DUPLICATED_EMAIL        = new CustomException(ExceptionStatus.DUPLICATED_EMAIL);
    public static final CustomException NONE_USER               = new CustomException(ExceptionStatus.NONE_USER);
    public static final CustomException MISMATCH_PASSWORD       = new CustomException(ExceptionStatus.MISMATCH_PASSWORD);
}
