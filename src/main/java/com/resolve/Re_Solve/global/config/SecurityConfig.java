package com.resolve.Re_Solve.global.config;

import com.resolve.Re_Solve.users.details.CustomUsersDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    public static final String SESSION_USER_ID = "LOGIN_USER_ID"; // 세션 키 상수

    @Bean
    SecurityFilterChain security(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/login", "/signup", "/css/**", "/js/**", "/images/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")               // GET 로그인 페이지
                        .loginProcessingUrl("/api/auth/login")      // POST 폼 전송(시큐리티가 처리)
                        .usernameParameter("email")        // 폼 input name="email"
                        .passwordParameter("password")     // 폼 input name="password"
                        .defaultSuccessUrl("/", true)      // 로그인 성공 후 이동
                        .successHandler((request, response, authentication) -> {
                            Object principal = authentication.getPrincipal();
                            if (principal instanceof CustomUsersDetails cud) {
                                request.getSession(true)
                                        .setAttribute(SESSION_USER_ID, cud.getUsersId()); // 세션에 Long 저장
                            }
                            // 기존 플로우대로 홈으로 이동
                            response.sendRedirect("/");
                        })
                        .failureUrl("/login?error")        // 실패 시
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")              // POST 전송 권장
                        .logoutSuccessUrl("/login?logout") // 성공 후 이동
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/auth/**")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                )
                .sessionManagement(sm -> sm
                        .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::migrateSession)
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                );
        return http.build();
    }

    @Bean BCryptPasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }
}
