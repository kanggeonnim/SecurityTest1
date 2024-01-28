package com.example.security2.auth;
// 시큐리티가 /login주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인을 진행이 완료가 되면 시큐리티 session을 만들어 줍니다. (security contextholder)
// 오브젝트타입 -> Authentication 객체
// Authentication안에 User정보가 있어야됨.
// User오브젝트타입=> UserDetails타입 객체

// Authentication안에는 Userdetails(일반로그인) , OAuth2User(소셜로그인) 필드 두종류만 들어갈 수 있다.
// Contoller단에서 유저정보를 가져오려면 두가지 종류의 객체를 적기불편하므로 Princ ipalDeatils에서 OAuth또한 상속받아서 두객체를 동시에 받을 수 있도록 만듬.
import com.example.security2.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// Security Session => Authentication => UserDetails(PrincipleDetails)

@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails, OAuth2User {

    private  User user;
    private  Map<String, Object> attributes;

    public User getUser() {
        return user;
    }
    public PrincipalDetails(User user) {
        this.user = user;
    }
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    // Oauth로그인
//    public PrincipalDetails(User user, Map<String, Object> attributes){
//        this.user = user;
//        this.attributes = attributes;
//    }
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    //해당 User의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoleList().forEach(r->{
            authorities.add(() -> r);
        });
        return authorities;
    }

    @Override
    public String getPassword() {

        return user.getPassword();
    }

    @Override
    public String getUsername() {

        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 우리 사이트에서 1년동안 회원이 로그인을 안하면 휴먼계정으로 전환하게 한다면 
        // 유저객체에 로그인시간 변수로저장후 현재시간-로그인시간
        // 이후 boolean으로 관리
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
