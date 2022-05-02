package com.toy.chanygram.repository;

import com.toy.chanygram.domain.Subscribe;
import com.toy.chanygram.dto.subscribe.SubscribeResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Query(value = "SELECT COUNT(s) FROM Subscribe s " +
            "WHERE s.fromUser.id = :principalId AND s.toUser.id = :pageUserId")
    int checkSubscribeState(@Param("principalId") long principalId,
                            @Param("pageUserId") long pageUserId);

    @Query(value = "SELECT COUNT(s) FROM Subscribe s " +
            "WHERE s.fromUser.id = :pageUserId")
    int countFollowing(@Param("pageUserId") long pageUserId);

    @Query(value =
            "SELECT new com.toy.chanygram.dto.subscribe." +
                    "SubscribeResponseDto(u.id, u.username, u.profileImageUrl, true, true) " +
            "FROM User u " +
            "INNER JOIN Subscribe s ON u.id = s.toUser.id " +
            "WHERE s.fromUser.id = :pageUserId"
    )
    Optional<List<SubscribeResponseDto>> followingList(@Param("pageUserId") long pageUserId);

    //"select new jpql.DTO.MemberDTO(m.username, m.age) " +
    //        "from Member m"
}
