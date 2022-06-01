package com.toy.chanygram.service;

import com.toy.chanygram.domain.Comment;
import com.toy.chanygram.domain.Image;
import com.toy.chanygram.domain.User;
import com.toy.chanygram.dto.comment.CommentResponseDto;
import com.toy.chanygram.exception.CustomValidationException;
import com.toy.chanygram.repository.CommentRepository;
import com.toy.chanygram.repository.ImageRepository;
import com.toy.chanygram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final CommentRepository commentRepository;

    public CommentResponseDto writeComment(Long userId, Long imageId, String content) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> {
                    log.info("댓글 저장 실패 : 존재하지 않는 회원입니다.");
                    return new CustomValidationException("댓글 저장 실패 : 존재하지 않는 회원입니다.", null);
                }
        );

        Image image = imageRepository.findById(imageId).orElseThrow(
                () -> {
                    log.info("댓글 저장 실패 : 존재하지 않는 이미지입니다.");
                    return new CustomValidationException("댓글 저장 실패 : 존재하지 않는 이미지입니다.", null);
                }
        );


        Comment comment = commentRepository.save(new Comment(content, user, image));
        return new CommentResponseDto(comment.getId(), user.getId(), user.getUsername(), content);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
