package com.server.controller;

import com.server.entity.Message;
import com.server.service.impl.MessageServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/message")
@CrossOrigin
@AllArgsConstructor
public class MessageController {

    private final MessageServiceImpl messageService;

    @GetMapping("/all")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(messageService.findAllMessages());
    }

    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody Message message) {
        return new ResponseEntity<>(messageService.createMessage(message), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Object> update(@RequestBody Message message) {
        return ResponseEntity.ok(messageService.updateMessage(message));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.deleteMessage(id));
    }
}
