package com.toy.chanygram.dto.image;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageSearchResponseDto {

    public Long imageId;
    public Long userId;
    public String postImageUrl;

}
