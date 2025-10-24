package com.resolve.Re_Solve.users;

import com.resolve.Re_Solve.global.annotation.LoginUsersId;
import com.resolve.Re_Solve.users.dto.ResUsersDto;
import com.resolve.Re_Solve.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/my-info")
    public ResUsersDto myInfo(@LoginUsersId Long usersId) {
        return usersService.getMyInfo(usersId);
    }

    @PostMapping("/off-email")
    public ResponseEntity<String> offEmail(@LoginUsersId Long usersId) {
        usersService.offEmailUsers(usersId);
        return ResponseEntity.ok().body("메일 알림 기능이 비활성화되었습니다.");
    }

    @PostMapping("/on-email")
    public ResponseEntity<String> onEmail(@LoginUsersId Long usersId) {
        usersService.onEmailUsers(usersId);
        return ResponseEntity.ok().body("메일 알림 기능이 활성화되었습니다.");
    }

    @PostMapping("/quit")
    public ResponseEntity<String> quit(@LoginUsersId Long usersId) {
        usersService.quit(usersId);
        return ResponseEntity
                .status(HttpStatus.SEE_OTHER) // 303 See Other
                .header(HttpHeaders.LOCATION, "/login")
                .build();
    }
}
