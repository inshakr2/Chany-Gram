package com.toy.chanygram.domain.user;

import com.toy.chanygram.domain.BaseTimeEntity;
import com.toy.chanygram.web.dto.auth.SignupDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static com.toy.chanygram.domain.user.Role.USER;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter(PROTECTED) @ToString
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, unique = true)
    private String username;

    private String password;

    private String email;
    private String name;
    private String website;
    private String aboutMe;
    private String phone;
    private String gender;

    private String profileImageUrl; // 프로필 사진

    @Enumerated(EnumType.STRING)
    private Role role = USER; // 권한

    public User(SignupDto signupDto) {
        this.username = signupDto.getUsername();
        this.password = signupDto.getPassword();
        this.email = signupDto.getEmail();
        this.name = signupDto.getName();
    }

}
