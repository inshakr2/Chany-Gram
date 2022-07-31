package com.toy.chanygram.dto.image;

import com.toy.chanygram.domain.User;
import com.toy.chanygram.dto.comment.CommentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    Long userId;
    String username;
    String userProfileImageUrl;

    // comment
    List<CommentResponseDto> comments;

    // tag
    List<String> tags;
}
