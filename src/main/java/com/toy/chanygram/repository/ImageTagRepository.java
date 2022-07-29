package com.toy.chanygram.repository;

import com.toy.chanygram.domain.ImageTag;
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
}
