package com.newsmanagementsystem.repository;

import com.newsmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    @Query(value = "SELECT U.id FROM User U WHERE U.userType='main_editor'")
    List<Long> findMainEditors();

    @Query(value = "SELECT U.id FROM User U WHERE U.userType='publisher_editor'")
    List<Long> findPublisherEditors();

    @Query(value = "SELECT U.id FROM User U WHERE U.userType = 'subscriber'")
    List<Long> findSubscriberUsers();

    @Query(value = "SELECT U.id FROM User U WHERE U.userType = 'non-subscriber'")
    List<Long> findNonSubscriberUsers();

    //@Query(value = "UPDATE user_ SET user_type='subscriber' WHERE id=:userId", nativeQuery = true)
    //ResponseEntity<HttpStatus> assignToSubscriber(Long userId);

}
