package com.server.service.impl;

import com.server.entity.Message;
import com.server.repository.MessageRepository;
import com.server.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public List<Message> findAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Message updateMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public String deleteMessage(Long id) {
        Message message = messageRepository.findById(id).orElseThrow();
        messageRepository.delete(message);
        return "Message was deleted with id: " + id;
    }
}
