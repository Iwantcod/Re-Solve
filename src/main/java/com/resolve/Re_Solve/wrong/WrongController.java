package com.resolve.Re_Solve.wrong;

import com.resolve.Re_Solve.global.annotation.LoginUsersId;
import com.resolve.Re_Solve.wrong.dto.ReqWrongDto;
import com.resolve.Re_Solve.wrong.service.WrongService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wrong")
public class WrongController {
    private final WrongService wrongService;

    @PostMapping
    public ResponseEntity<String> addWrong(@ModelAttribute @Valid ReqWrongDto dto, @LoginUsersId Long usersId) {
        wrongService.add(usersId, dto);
        return ResponseEntity.ok().body("추가되었습니다. 다시 풀어야될 때가되면 메일로 알려드릴게요!");
    }
}
