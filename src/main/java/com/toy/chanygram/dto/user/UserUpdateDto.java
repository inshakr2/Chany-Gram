package com.toy.chanygram.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

    // TODO: validation
    private String name;
    private String password;

    private String website;
    private String aboutMe;
    private String gender;
    private String phone;

    @Override
    public String toString() {
        return "UserUpdateDto(" +
                "name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ')';
    }
}
