package com.server.controller;

import com.server.entity.Chat;
import com.server.entity.Message;
import com.server.entity.request.ChatRequest;
import com.server.mapper.ChatMapper;
import com.server.mapper.MessageMapper;
import com.server.service.impl.ChatServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("api/chat")
@CrossOrigin
@AllArgsConstructor
public class ChatController {

    private final ChatServiceImpl chatService;

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Find all chats")
    @GetMapping("/all")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(chatService.findAllChats()
                .stream()
                .map(ChatMapper.INSTANCE::dtoFromEntity)
                .collect(Collectors.toList()));
    }

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Find chat by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getChat(@PathVariable Long id) {
        return ResponseEntity.ok(ChatMapper.INSTANCE.dtoFromEntity(chatService.findChatById(id)));
    }
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Find all chat by User")
    @GetMapping("/all/{userId}")
    public ResponseEntity<Object> getAllByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(chatService.findAllChatsByUser(userId)
                .stream()
                .map(ChatMapper.INSTANCE::dtoWithUsers)
                .collect(Collectors.toList()));
    }

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Create chat")
    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody ChatRequest chatRequest) {
        return new ResponseEntity<>(ChatMapper.INSTANCE.dtoFromEntity(chatService.createChat(chatRequest)), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Update chat")
    @PutMapping("/")
    public ResponseEntity<Object> update(@RequestBody Chat chat) {
        return ResponseEntity.ok(chatService.updateChat(chat));
    }

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Delete chat by chatID and Username")
    @DeleteMapping("/{chatId}/{username}")
    public ResponseEntity<Object> delete(@PathVariable Long chatId, @PathVariable String username) {
        return ResponseEntity.ok(chatService.deleteChat(chatId, username));
    }

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Get all chat messages")
    @GetMapping("/{id}/messages")
    public ResponseEntity<Object> getAllMessagesByChat(@PathVariable Long id) {
        return ResponseEntity.ok(chatService.getAllMessageByChat(id)
                .stream()
                .map(MessageMapper.INSTANCE::dtoFromEntity)
                .collect(Collectors.toList()));
    }

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Add message into chat")
    @PostMapping("/{id}/send")
    public ResponseEntity<Object> addMessageToChat(@PathVariable Long id, @RequestBody Message message) {
        return ResponseEntity.ok(ChatMapper.INSTANCE.dtoFromEntity(chatService.addMessageToChat(id, message)));
    }
}
