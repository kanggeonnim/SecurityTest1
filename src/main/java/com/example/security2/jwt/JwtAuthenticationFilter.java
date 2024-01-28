//package com.example.security2.jwt;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.example.security2.Entity.User;
//import com.example.security2.auth.PrincipalDetails;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import java.io.IOException;
//import java.util.Date;
//
////스프링 시큐리티에서 UsernamePasswordAuthenticationFilter가 있음.
//// /login 요청해서 username, password 전송하면(post)
//// UsernamePasswordAuthenticationFilter동작을 함. -> formLogin을 disable했기떄문에 동작을함. -> 추가적으로 등록필요.
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    private final AuthenticationManager authenticationManager;
//    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        System.out.println("JWT: 로그인시도중");
//        // 1. username, password 받아서
//        try {
//            // System.out.println(request.getInputStream().toString());
//            ObjectMapper om = new ObjectMapper();
//            User user = om.readValue(request.getInputStream(), User.class);
//            System.out.println(user.toString());
//
//            UsernamePasswordAuthenticationToken authenticationToken =
//                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
//
//            //PrincipalDetailsService의 loadByUsername()함수가 실행됨.
//            // authentication에 로그인한 정보가 담김.
//            Authentication authentication =
//                    authenticationManager.authenticate(authenticationToken);
//
//            // 리턴될때 authentication 객체가 session 영역에 저장됨.
//            // 리턴하는이유 -> 권한관리를 security가 대신 해주기때문에
//            // 굳이 JWT토큰을 사용하면서 세션을 만들이유가 없음. 단지 권한처리때문.
//
//            return authentication;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//    // attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication함수 실행
//    // JWT 토큰을 만들어서 request요청한 사용자에게 JWT토큰을 response해주면 됨.
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        System.out.println("successfullAuthentication 실행됨.");
//        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
//
//        String jwtToken = JWT.create()
//                .withSubject(principalDetails.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
//                .withClaim("id", principalDetails.getUser().getUser_id())
//                .withClaim("username", principalDetails.getUser().getUsername())
//                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
//
//        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX);
//    }
//}
