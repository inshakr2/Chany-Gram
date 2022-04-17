package com.toy.chanygram.repository;

import com.toy.chanygram.domain.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    // 퍼포먼스를 위해 네이티브 쿼리 이용...
    @Modifying
    @Query(value = "INSERT INTO subscribe(FromUser_ID, ToUser_ID, createdDate, lastModifiedDate) " +
            "VALUES (:fromUserId, :toUserId, now(), now())", nativeQuery = true)
    void subscribe(@Param("fromUserId") Long fromUserId, @Param("toUserId") Long toUserId);

    @Modifying
    @Query(value = "DELETE FROM subscribe WHERE " +
            "FromUser_ID = :fromUserId AND ToUser_ID = :toUserId", nativeQuery = true)
    void unSubscribe(@Param("fromUserId") Long fromUserId, @Param("toUserId") Long toUserId);
}
