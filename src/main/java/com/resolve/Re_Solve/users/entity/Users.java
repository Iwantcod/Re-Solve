package com.resolve.Re_Solve.users.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {
    @Id @GeneratedValue
    private Long usersId;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String username;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean isWanted;
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean isDeleted;
    private Users(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.role = RoleType.ROLE_USER;
        this.isWanted = true;
        this.isDeleted = false;
    }

    public static Users emailPasswordUsernameOf(String email, String password, String username) {
        return new Users(email, password, username);
    }

    public void setDeleted() {
        this.isDeleted = true;
    }
    public void unSetDeleted() {
        this.isDeleted = false;
    }
    public void setWanted() {
        this.isWanted = true;
    }
    public void unSetWanted() {
        this.isWanted = false;
    }
}
