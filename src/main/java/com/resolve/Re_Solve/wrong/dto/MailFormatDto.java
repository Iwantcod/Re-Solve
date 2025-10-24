package com.resolve.Re_Solve.wrong.dto;

import com.resolve.Re_Solve.platform.entity.PlatformType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
public class MailFormatDto {
    private String title;
    private String platformName;
    private Integer num;
    private Integer past; // 틀린게 며칠 전인지 저장
    private String url;
    public MailFormatDto(String title, PlatformType platform, Integer num, LocalDate past) {
        this.title = title;
        this.platformName = platform.name();
        this.num = num;
        this.past = (int) ChronoUnit.DAYS.between(past, LocalDate.now());
        this.url = platform.getUrl() + num;
    }

    @Override
    public String toString() {
        return "MailFormatDto{" +
                "title='" + title + '\'' +
                ", platformName='" + platformName + '\'' +
                ", num=" + num +
                ", past=" + past +
                ", url='" + url + '\'' +
                '}';
    }
}
