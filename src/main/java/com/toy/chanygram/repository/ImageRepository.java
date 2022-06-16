package com.toy.chanygram.repository;

import com.toy.chanygram.domain.Image;
import com.toy.chanygram.dto.image.ImagePopularDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT i FROM Image i " +
            "JOIN FETCH i.user u " +
            "WHERE (i.user.id IN (SELECT s.toUser.id FROM Subscribe s WHERE s.fromUser.id = :principalId) " +
            "OR i.user.id = :principalId) " +
            "AND i.id < :lastImageId")
    Slice<Image> getImageForStory(@Param("principalId") Long principalId,
                                  @Param("lastImageId") Long lastImageId, Pageable pageable);

    @Query("SELECT new com.toy.chanygram.dto.image.ImagePopularDto(i.id , COUNT(l) as cnt, i.postImageUrl, i.user.id) " +
            "FROM Image i " +
            "LEFT JOIN i.likes l " +
            "GROUP BY i.id " +
            "ORDER BY cnt DESC")
    List<ImagePopularDto> fetchPopularImage();

}
