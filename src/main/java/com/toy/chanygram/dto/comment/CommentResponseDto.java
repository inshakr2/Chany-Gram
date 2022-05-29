package com.toy.chanygram.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {

    private Long commentId;
    private String username;
    private String content;


}
