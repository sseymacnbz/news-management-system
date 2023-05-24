package com.newsmanagementsystem.service.impl;

import com.newsmanagementsystem.model.User;
import com.newsmanagementsystem.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, Integer> implements UserService {
}
