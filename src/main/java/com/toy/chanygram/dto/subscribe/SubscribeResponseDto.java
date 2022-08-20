package com.toy.chanygram.dto.subscribe;

import lombok.*;

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
