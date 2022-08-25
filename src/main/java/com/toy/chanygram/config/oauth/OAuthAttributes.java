package com.toy.chanygram.config.oauth;

import com.toy.chanygram.dto.auth.SignupDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@Slf4j
public class OAuthAttributes {

    public static SignupDto of(String registrationId, String nameAttributeKey, Map<String, Object> attributes) {

        if (registrationId.equalsIgnoreCase("facebook")) {
            return ofFacebook(nameAttributeKey, attributes);
        } else if (registrationId.equalsIgnoreCase("google")) {
            return ofGoogle(nameAttributeKey, attributes);
        } else if (registrationId.equalsIgnoreCase("kakao")) {
            return ofKakao(nameAttributeKey, attributes);
        } else if (registrationId.equalsIgnoreCase("naver")) {
            return ofNaver(nameAttributeKey, attributes);
        }else {
            return null;
        }
    }

    private static SignupDto ofNaver(String nameAttributeKey, Map<String, Object> attributes) {
        log.info("OAUTH2 Access : [NAVER]" );

        Map <String, Object> naver_account = (Map <String, Object>) attributes.get(nameAttributeKey);

        String id = (String) naver_account.get("id");

        String username = "NV_" + id.substring(0,22);
        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());

        String email = (String) naver_account.get("email");;
        String name = (String) naver_account.get("name");;

        return new SignupDto(username, password, email, name);
    }

    private static SignupDto ofKakao(String nameAttributeKey, Map<String, Object> attributes) {
        log.info("OAUTH2 Access : [KAKAO]");

        String username = "KK_" + String.valueOf(attributes.get(nameAttributeKey));
        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());

        Map <String, Object> kakao_account = (Map <String, Object>) attributes.get("kakao_account");
        String email = (String) kakao_account.get("email");

        Map <String, Object> properties = (Map <String, Object>) attributes.get("properties");
        String name = (String) properties.get("nickname");

        return new SignupDto(username, password, email, name);
    }

    private static SignupDto ofGoogle(String nameAttributeKey, Map<String, Object> attributes) {
        log.info("OAUTH2 Access : [GOOGLE]" );

        String username = "GG_" + (String) attributes.get(nameAttributeKey);
        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        return new SignupDto(username, password, email, name);
    }

    private static SignupDto ofFacebook(String nameAttributeKey, Map<String, Object> attributes) {
        log.info("OAUTH2 Access : [FACEBOOK]" );

        String username = "FB_" + (String) attributes.get(nameAttributeKey);
        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        return new SignupDto(username, password, email, name);
    }


}
