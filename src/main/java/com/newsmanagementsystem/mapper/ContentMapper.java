package com.newsmanagementsystem.mapper;

import com.newsmanagementsystem.dto.requests.CreateContentRequest;
import com.newsmanagementsystem.model.Content;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContentMapper {
    ContentMapper MAPPER = Mappers.getMapper(ContentMapper.class);

    Content toOrder(CreateContentRequest createContentRequest);

    @InheritInverseConfiguration
    CreateContentRequest fromContent(Content content);
}
