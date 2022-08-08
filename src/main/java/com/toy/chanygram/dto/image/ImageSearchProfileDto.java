package com.toy.chanygram.dto.image;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageSearchProfileDto {

    public Long tagId;
    public Long imageCount;
    public List<ImagePopularDto> popularImages;
}
