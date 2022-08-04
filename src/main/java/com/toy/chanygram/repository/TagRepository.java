package com.toy.chanygram.repository;

import com.toy.chanygram.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByTag(String tag);

    @Query("SELECT t.tag FROM Tag t WHERE t.id IN :tagIds")
    List<String> findTagsByIds(@Param("tagIds") List<Long> tagIds);

}
