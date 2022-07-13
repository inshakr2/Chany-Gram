package com.toy.chanygram.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchResultDto {

    private Long userId;
    private String username; // 아이디
    private String name; // 실제 이름
    private String userProfileImageUrl;

    private boolean subscribeStatus;
}
