package com.newsmanagementsystem.mapper;

import com.newsmanagementsystem.dto.requests.CreateNewsRequest;
import com.newsmanagementsystem.dto.requests.UpdateNewsRequest;
import com.newsmanagementsystem.model.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NewsMapper {
    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);


    @Mapping(target = "mainEditor.id", source = "mainEditorId")
    @Mapping(target = "content.id", source = "contentId")
    News createNewsRequestToNews(CreateNewsRequest createNewsRequest);



    @Mapping(target = "id", source = "newsId")
    @Mapping(target = "mainEditor.id", source = "mainEditorId")
    News updateNewsRequestToNews(UpdateNewsRequest updateNewsRequest);
}
