package com.resolve.Re_Solve.wrong.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReqWrongDto {
    @NotNull(message = "문제 플랫폼 식별자를 입력하세요.")
    private Integer platformId;
    @NotNull(message = "문제 번호를 입력하세요.")
    @Min(value = 1, message = "유효한 범위의 값을 입력하세요.") @Max(value = Integer.MAX_VALUE, message = "유효한 범위의 값을 입력하세요.")
    private Integer num;
    @NotNull(message = "문제를 틀린 날짜를 입력하세요.")
    private LocalDate date;
}
