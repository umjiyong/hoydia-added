package com.ssafy.hoydia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) { // 최신 issue : 브라우저 정책 바뀌어서 메소드 및 맵핑을 명시적으로 해야함
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("http://i7a103.p.ssafy.io:3000")
                .allowedOrigins("http://i7a103.p.ssafy.io")
                .allowedOrigins("http://localhost:3000")
//                .allowedOrigins("http://localhost:8080")
//                .allowedOrigins("http://localhost:80")
        ;
    }
}
