package com.server.dto;

import com.server.entity.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private Long id;
    private Long senderId;
    private String content;
    private LocalDateTime time;
    private MessageStatus status;
}
