package com.server.controller;

import com.server.entity.Message;
import com.server.service.impl.MessageServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/message")
@CrossOrigin
@AllArgsConstructor
public class MessageController {

    private final MessageServiceImpl messageService;

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Find all messages")
    @GetMapping("/all")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(messageService.findAllMessages());
    }

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Create message")
    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody Message message) {
        return new ResponseEntity<>(messageService.createMessage(message), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Update message")
    @PutMapping("/")
    public ResponseEntity<Object> update(@RequestBody Message message) {
        return ResponseEntity.ok(messageService.updateMessage(message));
    }

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Delete message")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.deleteMessage(id));
    }
}
