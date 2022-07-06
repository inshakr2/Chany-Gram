package com.toy.chanygram.config.oauth;

import com.toy.chanygram.dto.auth.SignupDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    public static SignupDto of(String registrationId, String nameAttributeKey, Map<String, Object> attributes) {

        if (registrationId.equalsIgnoreCase("facebook")) {
            return ofFacebook(nameAttributeKey, attributes);
        } else {
            return null;
        }
    }

    private static SignupDto ofFacebook(String nameAttributeKey, Map<String, Object> attributes) {

        String username = "FB_" + (String) attributes.get(nameAttributeKey);
        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        return new SignupDto(username, password, email, name);
    }


}
