package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.requests.CreateUserRequest;
import com.newsmanagementsystem.model.User;
import com.newsmanagementsystem.repository.UserRepository;
import com.newsmanagementsystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void create_shouldCreateSuccessfully(){
        User user = new User();
        //given
        when(userRepository.save(any())).thenReturn(user);

        // when
        CreateUserRequest createUserRequest = new CreateUserRequest();
        ResponseEntity<HttpStatus> response = userService.createSubscriber(createUserRequest);
        HttpStatus actual = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );

    }
}
