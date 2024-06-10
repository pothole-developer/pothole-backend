package pothole_solution.core.global.util.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import pothole_solution.core.global.exception.ExceptionStatus;

@Getter
public class BaseResponse<T> {

    private final Boolean isSuccess;
    private final int code;
    private final String message;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private T data;

    // 성공 시 반환할 데이터가 있을 때 반환되는 Response
    public BaseResponse(T data) {
        this.isSuccess = true;
        this.code = 1000;
        this.message = "요청에 성공하였습니다.";
        this.data = data;
    }

    // 성공 시 반환할 데이터가 없을 때 반환되는 Response
    public BaseResponse() {
        this.isSuccess = true;
        this.code = 1000;
        this.message = "요청에 성공하였습니다.";
    }

    // 실패 시 반환되는 Response
    public BaseResponse(ExceptionStatus status) {
        this.isSuccess = false;
        this.code = status.getCode();
        this.message = status.getMessage();
    }
}
