package com.server.mapper;

import com.server.dto.MessageDTO;
import com.server.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    Message entityFromDto(MessageDTO messageDTO);

    MessageDTO dtoFromEntity(Message message);
}
