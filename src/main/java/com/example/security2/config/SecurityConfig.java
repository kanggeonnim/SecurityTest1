package com.example.security2.config;

//import com.example.security2.filter.MyFilter3;
import com.example.security2.oauth.PrincipalOauth2UserService;
import com.example.security2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity//스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨.
@EnableMethodSecurity(securedEnabled = true)  // 시큐어 어노테이션 활성화, preAuthorize 어노테이션 활성화
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;

    private final CorsConfig corsConfig;

    private final UserRepository userRepository;
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

         AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        http
//                .addFilterBefore(new MyFilter3(), SecurityContextPersistenceFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                // h2-consel 허용
                .headers((headers)->
                        headers.contentTypeOptions(contentTypeOptionsConfig ->
                                headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)))
                // jwt 토큰사용시 session은 사용하지않음.
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

//                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable) // id,pw사용하는 basic 말고 토큰(bearer)사용하기위해 disable
//               .apply(new MyCustomDsl()) // 커스텀 필터 등록
//                .addFilter(new JwtAuthenticationFilter(authenticationManager))   // 로그인 진행하는 필터이기 때문에 AuthenticationManager가 필요함
                .headers(headers ->
                        headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/user/**").authenticated()
                                .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().permitAll())
//                .formLogin((formLogin) ->
//                        formLogin
//                                .loginPage(("/loginForm"))
//                                .loginProcessingUrl("/login")//login 주소가 호출되면 시큐리티가 낚아채서 대신 로그인을 진행해줌
//                                .defaultSuccessUrl("/"))
                .oauth2Login(oauth->
                        oauth   // 인증필요하면 이동
                                .loginPage("/loginForm")
                                .userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint
                                                .userService(principalOauth2UserService)));
        return http.build();
    }

//    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
//            http
//                    .addFilter(corsConfig.corsFilter())// @CorssOrigin(인증X), 시큐리티 필터등록 (인증O)
//                    .addFilter(new JwtAuthenticationFilter(authenticationManager))
//                    .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository));
//        }
//    }
}

