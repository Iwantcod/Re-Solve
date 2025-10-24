package com.resolve.Re_Solve.users.service;

import com.resolve.Re_Solve.users.UsersRepository;
import com.resolve.Re_Solve.users.details.CustomUsersDetails;
import com.resolve.Re_Solve.users.entity.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUsersDetailsService implements UserDetailsService {
    private final UsersRepository users;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users u = users.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("회원 정보를 찾을 수 없습니다."));
        return new CustomUsersDetails(
                u.getUsersId(),                       // ★ 식별자
                u.getEmail(),
                u.getPassword(),
                Set.of(new SimpleGrantedAuthority(u.getRole().name())),
                true
        );
    }
}
