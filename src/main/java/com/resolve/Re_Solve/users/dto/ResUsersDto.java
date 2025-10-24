package com.resolve.Re_Solve.users.dto;

import com.resolve.Re_Solve.users.entity.Users;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResUsersDto {
    private String email;
    private String username;
    private LocalDateTime createdAt;
    private Boolean isWanted;
    public ResUsersDto(Users users) {
        this.email = users.getEmail();
        this.username = users.getUsername();
        this.createdAt = users.getCreatedAt();
        this.isWanted = users.getIsWanted();
    }
}
