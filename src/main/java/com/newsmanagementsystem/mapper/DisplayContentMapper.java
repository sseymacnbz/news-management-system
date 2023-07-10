package com.newsmanagementsystem.mapper;

import com.newsmanagementsystem.dto.responses.DisplayContentsResponse;
import com.newsmanagementsystem.model.Content;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DisplayContentMapper {
    DisplayContentMapper INSTANCE = Mappers.getMapper(DisplayContentMapper.class);

    List<DisplayContentsResponse> contentToDisplayContentResponse(List<Content> contents);
}
