package com.toy.chanygram.controller.api;

import com.toy.chanygram.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/comment")
    public ResponseEntity<?> commentSave() {
        return null;
    }

    @DeleteMapping("/api/comment")
    public ResponseEntity<?> commentDelete() {
        return null;
    }
}
