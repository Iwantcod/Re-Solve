package com.resolve.Re_Solve.users;

import com.resolve.Re_Solve.global.annotation.LoginUsersId;
import com.resolve.Re_Solve.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    @GetMapping("/my-username")
    public String myUsername(@LoginUsersId Long usersId) {
        return usersService.getMyUsername(usersId);
    }
}
