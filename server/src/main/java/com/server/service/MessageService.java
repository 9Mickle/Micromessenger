package com.server.service;

import com.server.entity.Message;

import java.util.List;

public interface MessageService {

    List<Message> findAllMessages();

    Message createMessage(Message Message);

    Message updateMessage(Message Message);

    String deleteMessage(Long id);
}
