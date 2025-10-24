package com.resolve.Re_Solve.wrong.service;

import com.resolve.Re_Solve.global.advice.ApplicationError;
import com.resolve.Re_Solve.global.advice.ApplicationException;
import com.resolve.Re_Solve.mail.MailService;
import com.resolve.Re_Solve.platform.entity.Platform;
import com.resolve.Re_Solve.platform.PlatformRepository;
import com.resolve.Re_Solve.users.UsersRepository;
import com.resolve.Re_Solve.users.entity.Users;
import com.resolve.Re_Solve.wrong.Wrong;
import com.resolve.Re_Solve.wrong.WrongRepository;
import com.resolve.Re_Solve.wrong.dto.MailFormatDto;
import com.resolve.Re_Solve.wrong.dto.ReqWrongDto;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class WrongService {
    private final WrongRepository wrongRepository;
    private final UsersRepository usersRepository;
    private final PlatformRepository platformRepository;
    private final PageTitleService pageTitleService;
    private final MailService mailService;

    public void add(Long usersId, ReqWrongDto dto) {
        Platform platform = platformRepository.findById(dto.getPlatformId()).orElseThrow(() ->
                new ApplicationException(ApplicationError.PLATFORM_NOT_FOUND));
        Users users = usersRepository.findById(usersId).orElseThrow(() ->
                new ApplicationException(ApplicationError.USERS_NOT_FOUND));
        if(!users.getUsersId().equals(usersId)) {
            throw new ApplicationException(ApplicationError.FORBIDDEN_REQUEST);
        }
        Long todayCount = wrongRepository.countTodayAddByUsersIdAndDate(usersId, LocalDate.now());
        if(todayCount >= 10) {
            throw new ApplicationException(ApplicationError.FORBIDDEN_REQUEST);
        }

        String title = "";
        try {
            title = pageTitleService.fetchTitle(dto.getNum(), platform.getName());
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApplicationException(ApplicationError.CANNOT_ACCESS_SITE);
        }
        Wrong wrong = Wrong.platformUsersNumTitleDateOf(platform, users, dto.getNum(), title, dto.getDate());
        wrongRepository.save(wrong);
    }


    @Scheduled(cron = "0 0 4 * * *", zone = "Asia/Seoul")
    public void sendSchedule() {
        log.info("Daily reminder start: {}", LocalDateTime.now());
        List<Users> users = usersRepository.findAll();
        LocalDate today = LocalDate.now();
        List<LocalDate> dates = List.of(
                today.minusDays(1),
                today.minusDays(3),
                today.minusDays(7),
                today.minusDays(21)
        );

        for(Users user : users) {
            List<MailFormatDto> list = wrongRepository.findWrongByUsersId(user.getUsersId(), dates);
            if(list.isEmpty()) {
                continue;
            }
            try {
                mailService.sendReminder(user.getEmail(), user.getUsername(), list);
            } catch (MessagingException e) {
                System.out.println("메일 발송 실패: "+user.getEmail());
            }
        }

    }
}
