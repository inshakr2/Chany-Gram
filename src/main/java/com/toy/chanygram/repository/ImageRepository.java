package com.toy.chanygram.repository;

import com.toy.chanygram.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT i FROM Image i " +
            "JOIN FETCH i.user u " +
            "WHERE i.user.id IN (SELECT s.toUser.id FROM Subscribe s WHERE s.fromUser.id = :principalId)")
    List<Image> getImageForStory(@Param("principalId") Long principalId);

}
