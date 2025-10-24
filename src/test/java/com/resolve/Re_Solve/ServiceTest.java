package com.resolve.Re_Solve;

import com.resolve.Re_Solve.wrong.service.WrongService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceTest {
    private final WrongService wrongService;
    @Autowired
    public ServiceTest(WrongService wrongService) {
        this.wrongService = wrongService;
    }

    @Test
    @DisplayName("유저 별 틀린 문제 메일 발송")
    void findByWrong() {
        wrongService.sendSchedule();
    }
}
