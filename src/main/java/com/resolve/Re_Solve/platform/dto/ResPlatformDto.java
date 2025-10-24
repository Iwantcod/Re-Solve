package com.resolve.Re_Solve.platform.dto;

import com.resolve.Re_Solve.platform.entity.Platform;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResPlatformDto {
    private Integer platformId;
    private String name;
    private LocalDateTime createdAt;
    public ResPlatformDto(Platform platform) {
        this.platformId = platform.getPlatformId();
        this.name = platform.getName().name();
        this.createdAt = platform.getCreatedAt();
    }
}
