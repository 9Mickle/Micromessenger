package com.server.controller;

import com.server.dto.UserDTO;
import com.server.mapper.UserMapper;
import com.server.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user")
@CrossOrigin
@AllArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Find all users")
    @GetMapping("/all")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(userService.findAllUsers()
                .stream()
                .map(UserMapper.INSTANCE::dtoFromUser)
                .collect(Collectors.toList()));
    }

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Find User by username")
    @GetMapping("/")
    public ResponseEntity<Object> getByUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok(UserMapper.INSTANCE.dtoFromUser(userService.findByUsername(username)));
    }

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Update user")
    @PutMapping("/")
    public ResponseEntity<Object> update(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(UserMapper.INSTANCE.dtoFromUser(userService.updateUser(userDTO)));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Delete user by userID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
