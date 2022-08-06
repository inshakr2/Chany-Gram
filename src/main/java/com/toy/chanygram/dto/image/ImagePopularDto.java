package com.toy.chanygram.dto.image;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImagePopularDto {

    Long imageId;
    Long likesCount;
    String postImageUrl;
    Long userId;

    public ImagePopularDto(Long imageId, Long likesCount, String postImageUrl) {
        this.imageId = imageId;
        this.likesCount = likesCount;
        this.postImageUrl = postImageUrl;
    }
}
