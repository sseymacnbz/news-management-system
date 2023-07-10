package com.newsmanagementsystem.repository;

import com.newsmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    List<User> findByUserType(String userType);

    boolean existsUserById(Long id);

}
