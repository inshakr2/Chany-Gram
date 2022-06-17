package com.toy.chanygram.repository;

import com.toy.chanygram.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {


    @Modifying
    @Query(value = "INSERT INTO likes(FromUser_ID, ToImage_ID, createdDate, lastModifiedDate) " +
            "VALUES (:fromUserId, :toImageId, now(), now())", nativeQuery = true)
    int fetchLikes(@Param("fromUserId") Long principalId, @Param("toImageId") Long imageId);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE " +
            "FromUser_ID = :fromUserId AND ToImage_ID = :toImageId", nativeQuery = true)
    int fetchUnLikes(@Param("fromUserId") Long principalId, @Param("toImageId") Long imageId);

    @Modifying
    @Query("DELETE FROM Likes l WHERE l.user.id = :principalId")
    void deleteByFromUser(@Param("principalId") long principalId);
}
