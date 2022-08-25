package com.toy.chanygram.config.oauth;

import com.toy.chanygram.config.auth.PrincipalDetails;
import com.toy.chanygram.domain.User;
import com.toy.chanygram.dto.auth.SignupDto;
import com.toy.chanygram.exception.CustomValidationException;
import com.toy.chanygram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2DetailsService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeKey = userRequest.getClientRegistration()
                                            .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        Map<String, Object> userInfo = super.loadUser(userRequest).getAttributes();

        SignupDto signupDto = OAuthAttributes.of(registrationId, userNameAttributeKey, userInfo);

        if (signupDto == null) {
            log.error("OAUTH 로그인 실패. registrationId : " + registrationId);
            throw new CustomValidationException("지원하지 않는 소셜 로그인입니다.", null);
        }

        User userEntity = userRepository.findByUsername(signupDto.getUsername()).orElse(null);

        if (userEntity == null) {
            User authUser = new User(signupDto);
            log.info("New OAUTH user : [" + signupDto + "]");
            return new PrincipalDetails(userRepository.save(authUser), userInfo);

        } else {

            return new PrincipalDetails(userEntity, userInfo);
        }
    }
}
