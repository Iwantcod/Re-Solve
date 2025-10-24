package com.resolve.Re_Solve.platform;

import com.resolve.Re_Solve.platform.dto.ResPlatformDto;
import com.resolve.Re_Solve.platform.entity.Platform;
import com.resolve.Re_Solve.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlatformService {
    private final PlatformRepository platformRepository;
    private final UsersRepository usersRepository;

    // 모든 플랫폼 정보 조회
    public List<ResPlatformDto> getAllPlatforms() {
        List<Platform> platforms = platformRepository.findAll();
        return platforms.stream().map(ResPlatformDto::new).toList();
    }
}
