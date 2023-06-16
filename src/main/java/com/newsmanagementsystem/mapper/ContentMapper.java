package com.newsmanagementsystem.mapper;

import com.newsmanagementsystem.dto.requests.CreateContentRequest;
import com.newsmanagementsystem.model.Content;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContentMapper {
    ContentMapper INSTANCE = Mappers.getMapper(ContentMapper.class);

    @Mapping(target = "publisherEditor.id", source = "publisherEditorId")
    Content createContentRequestToContent(CreateContentRequest createContentRequest);


}

