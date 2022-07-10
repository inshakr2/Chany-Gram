package com.toy.chanygram.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.toy.chanygram.dto.auth.SignupDto;
import com.toy.chanygram.dto.user.UserUpdateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import java.util.List;

import static com.toy.chanygram.domain.Role.USER;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter(PROTECTED) @ToString
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25, nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    private String website;
    private String aboutMe;
    private String phone;
    private String gender;

    private String profileImageUrl; // 프로필 사진

    @Enumerated(EnumType.STRING)
    private Role role = USER; // 권한

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Image> images;

    public User(SignupDto signupDto) {
        this.username = signupDto.getUsername();
        this.password = signupDto.getPassword();
        this.email = signupDto.getEmail();
        this.name = signupDto.getName();
    }

    public void update(UserUpdateDto dto) {
        this.name = dto.getName();
        this.password = dto.getPassword();
        this.aboutMe = dto.getAboutMe();
        this.gender = dto.getGender();
        this.website = dto.getWebsite();
        this.phone = dto.getPhone();
    }

    public void changeProfileImage(String imageFileName) {
        this.profileImageUrl = imageFileName;
    }

}
