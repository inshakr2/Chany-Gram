package com.toy.chanygram.config.oauth;

import com.toy.chanygram.config.auth.PrincipalDetails;
import com.toy.chanygram.domain.User;
import com.toy.chanygram.dto.auth.SignupDto;
import com.toy.chanygram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuth2DetailsService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        Map<String, Object> userInfo = super.loadUser(userRequest).getAttributes();

        String username = "FB_" + userInfo.get("id");
        User userEntity = userRepository.findByUsername(username).orElse(null);

        if (userEntity == null) {
            User fbUser = new User(
                    new SignupDto(
                            username,
                            new BCryptPasswordEncoder().encode(UUID.randomUUID().toString()),
                            (String) userInfo.get("email"),
                            (String) userInfo.get("name")
                    )
            );

            return new PrincipalDetails(userRepository.save(fbUser), userInfo);

        } else {

            return new PrincipalDetails(userEntity, userInfo);
        }
    }
}
