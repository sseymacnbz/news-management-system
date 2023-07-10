package com.newsmanagementsystem.mapper;

import com.newsmanagementsystem.dto.requests.CreatePublisherEditorRequest;
import com.newsmanagementsystem.model.PublisherEditor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PublisherEditorMapper {
    PublisherEditorMapper INSTANCE = Mappers.getMapper(PublisherEditorMapper.class);

    PublisherEditor createPublisherEditorRequestToPublisherEditor(CreatePublisherEditorRequest createPublisherEditorRequest);
}
