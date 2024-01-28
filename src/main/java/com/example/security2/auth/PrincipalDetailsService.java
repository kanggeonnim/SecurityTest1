package com.example.security2.auth;

import com.example.security2.Entity.User;
import com.example.security2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//시큐리티 설정에서 loginProcessingUrl("/login");
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어있는 LoadUserByUsername함수가 실행
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    // html을 통한 username 파라미터 이름과 매칭시켜줘야함.

    // 시큐리티 session(내부 authentication(내부 userdetails))
    // UserDetails가 리턴될때 authentication 객체로 변하면서 시큐리티 session에 들어가짐.
    //함수 종료시@AuthenticationPrincipal 어노테이션이 만들어진다
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User UserEntity = userRepository.findByUsername(username);
        if(UserEntity != null){
            return new PrincipalDetails(UserEntity);
        }
        return null;
    }
}
