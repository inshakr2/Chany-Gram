package com.toy.chanygram.config.oauth;

import com.toy.chanygram.dto.auth.SignupDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
public class OAuthAttributes {

    public static SignupDto of(String registrationId, String nameAttributeKey, Map<String, Object> attributes) {

        if (registrationId.equalsIgnoreCase("facebook")) {
            return ofFacebook(nameAttributeKey, attributes);
        } else if (registrationId.equalsIgnoreCase("google")) {
            return ofGoogle(nameAttributeKey, attributes);
        } else {
            return null;
        }
    }

    private static SignupDto ofGoogle(String nameAttributeKey, Map<String, Object> attributes) {

        String username = "GOOGLE" + (String) attributes.get(nameAttributeKey);
        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        return new SignupDto(username, password, email, name);
    }

    private static SignupDto ofFacebook(String nameAttributeKey, Map<String, Object> attributes) {

        String username = "FB_" + (String) attributes.get(nameAttributeKey);
        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        return new SignupDto(username, password, email, name);
    }


}
