package com.newsmanagementsystem.repository;

import com.newsmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    @Query(value = "SELECT * from user_ WHERE user_type='main_editor'", nativeQuery = true)
    public List<User> findMainEditors();

    @Query(value = "SELECT * from user_ WHERE user_type='publisher_editor'", nativeQuery = true)
    public List<User> findPublisherEditors();

    @Query(value = "SELECT * from user_ WHERE user_type='public_user'", nativeQuery = true)
    public List<User> findUsers();
}
