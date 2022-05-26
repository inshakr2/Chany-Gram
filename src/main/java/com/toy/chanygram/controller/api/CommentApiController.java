package com.toy.chanygram.controller.api;

import com.toy.chanygram.config.auth.PrincipalDetails;
import com.toy.chanygram.domain.Comment;
import com.toy.chanygram.dto.CommonResponseDto;
import com.toy.chanygram.dto.comment.CommentDto;
import com.toy.chanygram.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/comment")
    public ResponseEntity<?> commentSave(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                         @RequestBody CommentDto commentDto) {

        Comment comment = commentService.writeComment(
                principalDetails.getUser().getId(), commentDto.getImageId(), commentDto.getContent()
        );


        return new ResponseEntity<>(
                new CommonResponseDto<>(1, "댓글 쓰기 성공", comment), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/comment")
    public ResponseEntity<?> commentDelete() {
        return null;
    }
}
