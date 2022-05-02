package com.toy.chanygram.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeResponseDto {

    private long userId;
    private String username;
    private String profileImageUrl;
    private boolean subscribeState;
    private boolean equalUserState;

}
