package com.resolve.Re_Solve.users.details;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUsersDetails implements UserDetails {
    @Getter
    private final Long usersId;                  // ★ 회원 식별자
    private final String username;          // email로 사용
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;

    public CustomUsersDetails(Long usersId, String email, String password,
                             Collection<? extends GrantedAuthority> authorities,
                             boolean enabled) {
        this.usersId = usersId;
        this.username = email;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
    }

    @Override public String getUsername() { return username; }
    @Override public String getPassword() { return password; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override public boolean isAccountNonExpired()  { return true; }
    @Override public boolean isAccountNonLocked()   { return true; }
    @Override public boolean isCredentialsNonExpired(){ return true; }
    @Override public boolean isEnabled()            { return enabled; }
}
