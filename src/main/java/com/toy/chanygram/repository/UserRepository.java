package com.toy.chanygram.repository;

import com.toy.chanygram.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    @Query("SELECT distinct u FROM User u " +
            "LEFT JOIN FETCH u.images i " +
            "LEFT JOIN i.likes " +
            "WHERE u.id = :userId")
    Optional<User> findUserWithImages(@Param("userId") Long userId);
}
