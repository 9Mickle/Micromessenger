package com.server.service.impl;

import com.server.entity.Chat;
import com.server.entity.Message;
import com.server.entity.User;
import com.server.entity.request.ChatRequest;
import com.server.exception.NotFoundException;
import com.server.repository.ChatRepository;
import com.server.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserServiceImpl userService;

    @Override
    public List<Chat> findAllChats() {
        return chatRepository.findAll();
    }

    @Override
    public Chat findChatById(Long id) {
        return chatRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Chat not found with id: " + id));
    }

    @Override
    public List<Chat> findAllChatsByUser(Long id) {
        return chatRepository.findAllByUserId(id);
    }

    @Override
    public Chat createChat(ChatRequest chatRequest) {
        Set<User> users = chatRequest.getUsernames().stream()
                .map(userService::findByUsername)
                .collect(Collectors.toSet());
        Chat chat = Chat.builder()
                .title(chatRequest.getTitle())
                .users(users)
                .build();
        users.forEach(user -> user.getChats().add(chat));
        return chatRepository.save(chat);
    }

    @Override
    public Chat updateChat(Chat chat) {
        return chatRepository.save(chat);
    }

    @Override
    public String deleteChat(Long chatId, String username) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new NotFoundException("Chat not found with id: " + chatId));
        User user = userService.findByUsername(username);
        chat.removeUser(user);
        chatRepository.save(chat);
        if (chat.getUsers().size() == 0) {
            chatRepository.delete(chat);
            return "Chat was deleted with id: " + chatId;
        } else return String.format("%s leave chat", username);
    }

    @Override
    public Chat addMessageToChat(Long chatId, Message message) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new NotFoundException("Chat not found with id: " + chatId));
        message.setTime(LocalDateTime.now());
        message.setChat(chat);
        chat.getMessages().add(message);
        return chatRepository.save(chat);
    }

    @Override
    public List<Message> getAllMessageByChat(Long chatId) {
        return chatRepository.findById(chatId)
                .orElseThrow(() -> new NotFoundException("Chat not found with id: " + chatId))
                .getMessages();
    }
}
