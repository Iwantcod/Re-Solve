package com.resolve.Re_Solve.global.advice;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApplicationError {
    USERS_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    PLATFORM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 플랫폼입니다."),
    CANNOT_ACCESS_SITE(HttpStatus.BAD_REQUEST, "문제 플랫폼 사이트에 접근할 수 없습니다."),
    NOT_FOUND_PROBLEM(HttpStatus.NOT_FOUND, "문제를 찾을 수 없습니다."),
    FORBIDDEN_REQUEST(HttpStatus.FORBIDDEN, "잘못된 요청입니다."),
    OVER_REQUEST(HttpStatus.BAD_REQUEST, "하루에 문제를 10개까지만 추가할 수 있습니다."),
    EMAIL_DUP(HttpStatus.BAD_REQUEST, "이미 사용 중인 이메일입니다.");


    private final HttpStatus status;
    private final String message;
    ApplicationError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
