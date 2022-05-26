package com.server.controller;

import com.server.dto.UserDTO;
import com.server.mapper.UserMapper;
import com.server.service.impl.UserServiceImpl;
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
    @GetMapping("/all")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(userService.findAllUsers()
                .stream()
                .map(UserMapper.INSTANCE::dtoFromUser)
                .collect(Collectors.toList()));
    }

//    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
//    @GetMapping("/all/?usernames=")
//    public ResponseEntity<Object> getAllUsersByUsernames(@RequestParam List<String> usernames) {
//        System.out.println(usernames);
//        return ResponseEntity.ok(userService.findUsersByUsernames(usernames));
//    }

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    @GetMapping("/")
    public ResponseEntity<Object> getByUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok(UserMapper.INSTANCE.dtoFromUser(userService.findByUsername(username)));
    }

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    @PutMapping("/")
    public ResponseEntity<Object> update(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(UserMapper.INSTANCE.dtoFromUser(userService.updateUser(userDTO)));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
