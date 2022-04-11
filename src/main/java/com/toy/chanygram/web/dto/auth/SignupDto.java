package com.toy.chanygram.web.dto.auth;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class SignupDto {

    private String username;
    private String password;
    private String email;
    private String name;
}
