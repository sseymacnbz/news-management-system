package com.newsmanagementsystem.controller;

import com.newsmanagementsystem.dto.DenemeDTO;
import com.newsmanagementsystem.model.User;
import com.newsmanagementsystem.model.enums.UserType;
import com.newsmanagementsystem.service.BaseService;
import com.newsmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class PublisherEditorController {
    @Autowired
    private UserService userService;

    public void save(User user){
        if(user.getType().equals(UserType.PUBLISHER_EDITOR)){
            userService.save(user);
        }

    }
}
