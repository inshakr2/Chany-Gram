package com.toy.chanygram.dto.image;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImageEditRequestDto {

    public Long imageId;
    public String caption;
    public String orgTag;
    public String newTag;

}
