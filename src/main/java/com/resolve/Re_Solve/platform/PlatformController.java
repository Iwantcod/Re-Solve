package com.resolve.Re_Solve.platform;

import com.resolve.Re_Solve.platform.dto.ResPlatformDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/platform")
public class PlatformController {
    private final PlatformService platformService;

    @GetMapping("/all")
    public List<ResPlatformDto> getAllPlatforms() {
        return platformService.getAllPlatforms();
    }
}
