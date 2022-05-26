package com.server.mapper;

import com.server.dto.ChatDTO;
import com.server.dto.ChatWithUserDTO;
import com.server.entity.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);

    Chat entityFromDto(ChatDTO dto);

    ChatDTO dtoFromEntity(Chat entity);

    ChatWithUserDTO dtoWithUsers(Chat entity);
}
