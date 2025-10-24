package com.resolve.Re_Solve.global.config;

import com.resolve.Re_Solve.global.annotation.LoginUserIdArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final LoginUserIdArgumentResolver loginUserIdArgumentResolver;

    public WebMvcConfig(LoginUserIdArgumentResolver resolver) {
        this.loginUserIdArgumentResolver = resolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserIdArgumentResolver);
    }
}
