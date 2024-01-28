package com.example.security2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);   // 내 서버가 응답할때 json을 자바스크립트에서 처리할 수 있게 해주기
        config.addAllowedOrigin("*");   // 모든 ip에 응답 허용
        config.addAllowedHeader("*");   // 모든 헤더에 응답 허용
        config.addAllowedMethod("*");   // 모든 메서드 응답 허용
        source.registerCorsConfiguration("/", config);  // "/api/**"와 같이 지정해도 됨
        return new CorsFilter(source);
    }
}
