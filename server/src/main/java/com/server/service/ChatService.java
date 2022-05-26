package com.server.service;

import com.server.entity.Chat;
import com.server.entity.request.ChatRequest;
import com.server.entity.Message;

import java.util.List;

public interface ChatService {

    List<Chat> findAllChats();

    List<Chat> findAllChatsByUser(Long id);

    Chat findChatById(Long id);

    Chat createChat(ChatRequest chatRequest);

    Chat updateChat(Chat chat);

    String deleteChat(Long chatId, String username);

    Chat addMessageToChat(Long chatId, Message message);

    List<Message> getAllMessageByChat(Long id);
}
