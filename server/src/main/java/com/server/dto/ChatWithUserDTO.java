package com.server.dto;

import com.server.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatWithUserDTO {
    private Long id;
    private String title;
    private Set<Message> messages;
    private Set<UserDTO> users;
}
