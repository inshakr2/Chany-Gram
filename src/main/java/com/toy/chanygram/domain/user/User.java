package com.toy.chanygram.domain.user;

import com.toy.chanygram.domain.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter(PROTECTED)
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;
    private String name;
    private String website;
    private String aboutMe;
    private String phone;
    private String gender;

    private String profileImageUrl; // 프로필 사진
    private String role; // 권한
}
