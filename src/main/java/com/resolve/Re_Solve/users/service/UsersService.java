package com.resolve.Re_Solve.users.service;

import com.resolve.Re_Solve.global.advice.ApplicationError;
import com.resolve.Re_Solve.global.advice.ApplicationException;
import com.resolve.Re_Solve.users.UsersRepository;
import com.resolve.Re_Solve.users.dto.ReqUsersDto;
import com.resolve.Re_Solve.users.dto.ResUsersDto;
import com.resolve.Re_Solve.users.entity.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(ReqUsersDto dto) {
        if(usersRepository.existsByEmail(dto.getEmail())) {
            throw new ApplicationException(ApplicationError.EMAIL_DUP);
        }
        String password = bCryptPasswordEncoder.encode(dto.getPassword());
        Users users = Users.emailPasswordUsernameOf(dto.getEmail(), password, dto.getUsername());
        usersRepository.save(users);
    }

    public boolean checkEmail(String email) {
        return usersRepository.existsByEmail(email);
    }

    public String getMyUsername(Long usersId) {
        String username = usersRepository.findUsernameById(usersId);
        return username;
    }

    public ResUsersDto getMyInfo(Long usersId) {
        Users users = usersRepository.findById(usersId).orElseThrow(() ->
                new ApplicationException(ApplicationError.USERS_NOT_FOUND));
        return new ResUsersDto(users);
    }

    public void offEmailUsers(Long usersId) {
        Users users = usersRepository.findById(usersId).orElseThrow(() ->
                new ApplicationException(ApplicationError.USERS_NOT_FOUND));
        users.unSetWanted();
        usersRepository.save(users);
    }

    public void onEmailUsers(Long usersId) {
        Users users = usersRepository.findById(usersId).orElseThrow(() ->
                new ApplicationException(ApplicationError.USERS_NOT_FOUND));
        users.setWanted();
        usersRepository.save(users);
    }

    public void quit(Long usersId) {
        Users users = usersRepository.findById(usersId).orElseThrow(() ->
                new ApplicationException(ApplicationError.USERS_NOT_FOUND));
        users.setDeleted();
        users.unSetWanted();
        usersRepository.save(users);
    }

}
