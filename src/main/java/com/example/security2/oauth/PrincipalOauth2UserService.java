package com.example.security2.oauth;

import com.example.security2.Entity.User;
import com.example.security2.auth.PrincipalDetails;
import com.example.security2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    // 구글, 카카오로 부터 받은 userRequest 데이터에 대한 후처리 되는 함수
    //함수 종료시@AuthenticationPrincipal 어노테이션이 만들어진다
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 소셜로그인 버튼 클릭 -> 소셜로그인창 -> 로그인완료 -> code를 리턴(Oauth-Client라이브러리) ->AccessToken요청
        // userRequest 정보 -> 회원프로필 받아야함(loadUser함수 호출) -> 구글,카카오로부터 회원프로필을 받아준다.

        OAuth2User oAuth2User = super.loadUser(userRequest);

        //
        String provider = userRequest.getClientRegistration().getClientId(); // google or kakao
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider + "_" + providerId;     //
        String email = oAuth2User.getAttribute("email");
        String roles = "ROLE_USER";

        User userEntity = userRepository.findByUsername(username);

        // 회원이 존재하지 않을경우
        if(userEntity == null){
            userEntity = User.builder()
                    .username(username)
                    .email(email)
                    .provider(provider)
                    .providerId(providerId)
                    .roles(roles)
                    .build();
            userRepository.save(userEntity);
        }

        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}
