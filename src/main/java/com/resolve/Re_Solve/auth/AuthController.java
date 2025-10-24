package com.resolve.Re_Solve.auth;

import com.resolve.Re_Solve.users.service.UsersService;
import com.resolve.Re_Solve.users.dto.ReqUsersDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final UsersService usersService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@ModelAttribute @Valid ReqUsersDto dto) {
        usersService.join(dto);
        return ResponseEntity
                .status(HttpStatus.SEE_OTHER) // 303 See Other
                .header(HttpHeaders.LOCATION, "/login?signupSuccess")
                .build();
    }

    @GetMapping("/check-email/{email}")
    public ResponseEntity<String> checkEmail(@PathVariable String email) {
        if(usersService.checkEmail(email)) {
           return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용중인 이메일입니다.");
        } else {
            return ResponseEntity.ok().body("사용 가능한 이메일입니다.");
        }
    }
}
