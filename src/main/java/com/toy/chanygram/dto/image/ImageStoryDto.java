package com.toy.chanygram.dto.image;

import com.toy.chanygram.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageStoryDto {

    // Image
    Long imageId;
    String postImageUrl;
    String caption;
    boolean likeStatus;
    int likeCount;


    // User
    String username;
    String userProfileImageUrl;

}
