package com.toy.chanygram.controller.api;

import com.toy.chanygram.config.auth.PrincipalDetails;
import com.toy.chanygram.domain.Comment;
import com.toy.chanygram.dto.CommonResponseDto;
import com.toy.chanygram.dto.comment.CommentDto;
import com.toy.chanygram.dto.comment.CommentResponseDto;
import com.toy.chanygram.exception.CustomValidationApiException;
import com.toy.chanygram.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/comment")
    public ResponseEntity<?> commentSave(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                         @Valid @RequestBody CommentDto commentDto,
                                         Errors errors) {

        CommentResponseDto commentResponseDto = commentService.writeComment(
                principalDetails.getUser().getId(), commentDto.getImageId(), commentDto.getContent()
        );


        return new ResponseEntity<>(
                new CommonResponseDto<>(1, "댓글 쓰기 성공", commentResponseDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/comment/{commentId}")
    public ResponseEntity<?> commentDelete(@PathVariable Long commentId) {

        commentService.deleteComment(commentId);
        return new ResponseEntity<>(
                new CommonResponseDto<>(1, "댓글 삭제 성공", null), HttpStatus.OK);
    }
}
