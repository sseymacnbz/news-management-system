package com.newsmanagementsystem.mapper;

import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.model.News;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DisplayNewsMapper {
    DisplayNewsMapper INSTANCE = Mappers.getMapper(DisplayNewsMapper.class);
    List<DisplayNewsResponse> newsToDisplayNewsResponse(List<News> news);
}
