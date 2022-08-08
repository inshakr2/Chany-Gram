package com.toy.chanygram.dto.image;

import lombok.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageSearchPagingDto {

    public List<Long> popularIds;
    public Long tagId;
    public Long lastImageId;
}
