package com.newsmanagementsystem.service.impl;

import com.newsmanagementsystem.model.User;
import com.newsmanagementsystem.repository.UserRepository;
import com.newsmanagementsystem.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public void create(User user) {
        userRepository.save(user);
    }
}
