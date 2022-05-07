package com.toy.chanygram.dto.user;

import com.toy.chanygram.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {

    private boolean pageOwner;
    private boolean subscribeState;
    private int following;
    private int follower;
    private int imageCount;
    private User user;
}
