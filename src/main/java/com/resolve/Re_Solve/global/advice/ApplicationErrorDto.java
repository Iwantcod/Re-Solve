package com.resolve.Re_Solve.global.advice;

import com.resolve.Re_Solve.util.ExceptionUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationErrorDto {
    private int status;
    private String method;
    private String path;
    private String message;
    private LocalDateTime timestamp;


    private ApplicationErrorDto(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
        this.timestamp = LocalDateTime.now();

        RequestPathDto requestPath = ExceptionUtil.getRequestPath(); // 해당 요청의 url 및 method 정보를 담은 객체 load
        if(requestPath != null) {
            this.path = requestPath.getPath();
            this.method = requestPath.getHttpMethod();
        }
    }
    public static ApplicationErrorDto statusMessageOf(HttpStatus status, String message) {
        return new ApplicationErrorDto(status, message);
    }
}
