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
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT i FROM Image i " +
            "JOIN FETCH i.user u "+
            "WHERE (i.user.id IN (SELECT s.toUser.id FROM Subscribe s WHERE s.fromUser.id = :principalId) " +
            "OR i.user.id = :principalId) " +
            "AND i.id < :lastImageId")
    Slice<Image> getImageForStory(@Param("principalId") Long principalId,
                                  @Param("lastImageId") Long lastImageId, Pageable pageable);

    @Query("SELECT i FROM Image i " +
            "JOIN FETCH i.user u " +
            "WHERE (i.id IN (SELECT it.image.id FROM ImageTag it WHERE it.tag.id = :tagId)) " +
            "AND i.id < :lastImageId")
    Slice<Image> getImageFromTag(@Param("lastImageId") Long lastImageId,
                                 @Param("tagId") Long tagId, Pageable pageable);

    @Query("SELECT i FROM Image i " +
            "JOIN FETCH i.user u " +
            "LEFT JOIN i.tags t " +
            "WHERE i.id = :imageId")
    Optional<Image> getImageForDetail(@Param("imageId") Long imageId);

    @Query("SELECT new com.toy.chanygram.dto.image.ImagePopularDto(i.id , COUNT(l) as cnt, i.postImageUrl, i.user.id) " +
            "FROM Image i " +
            "LEFT JOIN i.likes l " +
            "GROUP BY i.id " +
            "ORDER BY cnt DESC")
    List<ImagePopularDto> fetchPopularImage();

    @Query("SELECT i FROM Image i " +
            "JOIN FETCH i.user u " +
            "LEFT JOIN i.tags t " +
            "WHERE i.id = :imageId")
    Optional<Image> findImageWithOwner(@Param("imageId") Long imageId);

}
