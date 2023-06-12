package com.newsmanagementsystem.repository;

import com.newsmanagementsystem.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    @Query(value = "SELECT * from user_ WHERE user_type='main_editor'", nativeQuery = true)
    public List<User> findMainEditors();

    @Query(value = "SELECT * from user_ WHERE user_type='publisher_editor'", nativeQuery = true)
    public List<User> findPublisherEditors();

    @Query(value = "SELECT * from user_ WHERE user_type = 'subscriber'", nativeQuery = true)
    public List<User> findSubscriberUsers();

    @Query(value = "SELECT * from user_ WHERE user_type = 'non-subscriber'", nativeQuery = true)
    public List<User> findNonSubscriberUsers();

    public boolean existsUserById (Long userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_ SET user_type = 'publisher_editor' WHERE id = :id", nativeQuery = true)
    public void assignToPublisherEditor(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_ SET user_type = 'subscriber' WHERE id = :id", nativeQuery = true)
    public void assignToSubscriber(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_ SET user_type = 'main_editor' WHERE id = :id", nativeQuery = true)
    public void assignToMainEditor(@Param("id") Long id);

}
