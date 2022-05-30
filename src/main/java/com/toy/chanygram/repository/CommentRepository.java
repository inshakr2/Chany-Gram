package com.toy.chanygram.repository;

import com.toy.chanygram.domain.Comment;
import com.toy.chanygram.dto.comment.CommentResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT com.toy.chanygram.dto.comment.CommentResponseDto(c.id, u.username, c.content)" +
            "FROM Comment c " +
            "JOIN FETCH c.user u " +
            "WHERE c.image.id = :imageId")
    List<CommentResponseDto> findByImageForStory(@Param("imageId") Long imageId);

}
