package com.resolve.Re_Solve.wrong;

import com.resolve.Re_Solve.platform.entity.Platform;
import com.resolve.Re_Solve.users.entity.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wrong {
    @Id @GeneratedValue
    private Long wrongId;
    @JoinColumn(name = "platform_id", nullable = false)
    @ManyToOne
    private Platform platform;
    @JoinColumn(name = "users_id", nullable = false)
    @ManyToOne
    private Users users;
    @Column(nullable = false)
    private Integer num;
    private String title; // null 허용: 크롤링 안되는 경우 고려
    @Column(nullable = false)
    private LocalDate date;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    private Wrong(Platform platform, Users users, Integer num, String title, LocalDate date) {
        this.platform = platform;
        this.users = users;
        this.num = num;
        this.title = title;
        this.date = date;
    }
    public static Wrong platformUsersNumTitleDateOf(Platform platform, Users users, Integer num, String title, LocalDate date) {
        return new Wrong(platform, users, num, title, date);
    }
}
