package com.toy.chanygram.repository;

import com.toy.chanygram.domain.User;
import com.toy.chanygram.dto.user.UserSearchResultDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    @Query("SELECT distinct u FROM User u " +
            "LEFT JOIN FETCH u.images i " +
            "LEFT JOIN i.likes " +
            "WHERE u.id = :userId")
    Optional<User> findUserWithImagesAndLikes(@Param("userId") Long userId);

    @Query("SELECT new com.toy.chanygram.dto.user.UserSearchResultDto" +
            "(u.id, u.username, u.name, u.profileImageUrl, " +
            "(SELECT COUNT(s.id) > 0 FROM Subscribe s WHERE s.fromUser.id = :principalId AND s.toUser.id = u.id)) " +
            "FROM User u " +
            "LEFT OUTER JOIN Subscribe s ON u.id = s.fromUser.id " +
            "WHERE u.username LIKE CONCAT('%',:username,'%')")
    List<UserSearchResultDto> searchByUsername(@Param("username") String username,
                                               @Param("principalId") Long principalId);
}