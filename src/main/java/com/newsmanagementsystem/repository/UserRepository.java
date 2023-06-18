package com.newsmanagementsystem.repository;

import com.newsmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    @Query(value = "SELECT U.id FROM User U WHERE U.userType='main_editor'")
    List<User> findMainEditors();

    @Query(value = "SELECT U.id FROM User U WHERE U.userType='publisher_editor'")
    List<User> findPublisherEditors();

    @Query(value = "SELECT U.id FROM User U WHERE U.userType = 'subscriber'")
    List<User> findSubscriberUsers();

    @Query(value = "SELECT U.id FROM User U WHERE U.userType = 'non-subscriber'")
    List<User> findNonSubscriberUsers();

}
