package com.example.security2.controller;

import com.example.security2.Entity.User;
import com.example.security2.auth.PrincipalDetails;
import com.example.security2.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Transactional
@Controller
public class IndexController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/login")
    public String loginTest(Authentication authentication){
        System.out.println("/test/login+++");
        System.out.println(authentication.getPrincipal());
        return "세션";
    }
    @GetMapping({"", "/"})
    public String index() {
        return "index";
    }


    // PrincipalDetails가 UserDetails와 Oauth2User를 둘다 implements하고있기떄문에 어떤걸로 둘어오든 정보를 받을 수 있음.
    @GetMapping("/user")
    @ResponseBody
    public String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("principalDetails: " + principalDetails.getAttributes());

        return "user";
    }

    @GetMapping("/manager")
    @ResponseBody
    public String manager() {
        return "manager";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "admin";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return
                "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        System.out.println(user);
        user.setRoles("ROLE_USER");
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user);
        return "redirect:/loginForm";
    }


    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public String info(){
        return "개인정보";
    }

    // 함수가 실행되기면에 권한확인 -> preAuthorize, 함수가 실행된후 권한확인 postAuthorize
    // @preAuthorize(권한여러개 주고싶을때) 하나만 주고싶으면 시큐어써도됨.
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/data")
    public String data(){
        return "데이타정보";
    }

    @GetMapping("/home")
    @ResponseBody
    public String home(){
        return "Home";
    }

    @PostMapping("/token")
    @ResponseBody
    public String token(){
        return "token";
    }

}
