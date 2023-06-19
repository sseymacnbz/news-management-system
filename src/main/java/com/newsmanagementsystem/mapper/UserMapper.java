package com.newsmanagementsystem.mapper;

import com.newsmanagementsystem.dto.requests.CreateUserRequest;
import com.newsmanagementsystem.model.PublisherEditor;
import com.newsmanagementsystem.model.Subscriber;
import com.newsmanagementsystem.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    Subscriber convertToSubscriber(CreateUserRequest createPublicUserRequest);

    Subscriber convertToSubscriber(User user);

    PublisherEditor convertToPublisherEditor(User user);
}
