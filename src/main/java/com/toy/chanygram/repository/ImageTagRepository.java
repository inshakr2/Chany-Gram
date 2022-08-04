package com.toy.chanygram.repository;

import com.toy.chanygram.domain.ImageTag;
import com.toy.chanygram.dto.image.ImageProfileDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageTagRepository extends JpaRepository<ImageTag, Long> {

    @Modifying
    @Query(value = "INSERT INTO imageTag(Image_ID, Tag_ID, createdDate, lastModifiedDate) " +
            "VALUES (:imageId, :tagId, now(), now())", nativeQuery = true)
    void mappingImageWithTags(@Param("imageId") Long imageId, @Param("tagId") Long tagId);

    @Modifying
    @Query("DELETE FROM ImageTag it WHERE it.image.id = :imageId")
    void deleteByImageId(@Param("imageId") Long imageId);


    @Query("SELECT new com.toy.chanygram.dto.image.ImageProfileDto(i.postImageUrl, COUNT(l) as CNT) " +
            "FROM ImageTag it " +
            "JOIN FETCH it.image i " +
            "LEFT JOIN i.likes l " +
            "GROUP BY i.id " +
            "ORDER BY cnt DESC")
    Slice<ImageProfileDto> getTopImageProfileFromTag(Long tagId, Pageable pageable);

}
