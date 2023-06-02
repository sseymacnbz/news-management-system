package com.newsmanagementsystem.mapper;

import com.newsmanagementsystem.dto.requests.CreateNewsRequest;
import com.newsmanagementsystem.model.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NewsMapper {
    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    @Mappings({
            @Mapping(target = "mainEditor.id", source = "mainEditorId"),
            @Mapping(target = "content.id", source = "contentId")
    })
    News createNewsRequestToContent(CreateNewsRequest createNewsRequest);
}
