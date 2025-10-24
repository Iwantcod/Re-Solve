package com.resolve.Re_Solve.platform.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Platform {
    @Id @GeneratedValue
    private Integer platformId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PlatformType name; // 영문이름으로 저장
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean isDeleted;

    private Platform(PlatformType name) {
        this.name = name;
        this.isDeleted = false;
    }
    public static Platform nameFrom(PlatformType name) {
        return new Platform(name);
    }

    public void setDeleted() {
        this.isDeleted = true;
    }
    public void unSetDeleted() {
        this.isDeleted = false;
    }
}
