package com.server.service;

import com.server.dto.UserDTO;
import com.server.entity.Chat;
import com.server.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAllUsers();
    List<User> findUsersByUsernames(List<String> usernames);

    User findById(Long id);

    User createUser(User user);

    String activateUser(String code);

    User updateUser(UserDTO userDTO);

    String deleteUser(Long id);

    User findByUsername(String username);
}
