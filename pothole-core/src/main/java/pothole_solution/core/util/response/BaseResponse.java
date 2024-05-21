package pothole_solution.core.util.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import pothole_solution.core.util.exception.ExceptionStatus;

@Getter
public class BaseResponse<T> {

    private final Boolean isSuccess;
    private final int code;
    private final String message;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private T data;

    // 성공시 반환되는 Response
    public BaseResponse(T data) {
        this.isSuccess = true;
        this.code = 1000;
        this.message = "요청에 성공하였습니다.";
        this.data = data;
    }

    // 실패시 반환되는 Response
    public BaseResponse(ExceptionStatus status) {
        this.isSuccess = false;
        this.code = status.getCode();
        this.message = status.getMessage();
    }
}
