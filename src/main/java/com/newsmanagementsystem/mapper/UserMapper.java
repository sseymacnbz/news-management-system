package com.newsmanagementsystem.mapper;

import com.newsmanagementsystem.dto.requests.CreatePublicUserRequest;
import com.newsmanagementsystem.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User createPublicUserRequest(CreatePublicUserRequest createPublicUserRequest);
}
