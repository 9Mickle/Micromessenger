package com.server.service.impl;

import com.server.dto.UserDTO;
import com.server.entity.User;
import com.server.exception.NotFoundException;
import com.server.mapper.UserMapper;
import com.server.repository.UserRepository;
import com.server.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found by username " + username));
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findUsersByUsernames(List<String> usernames) {
        System.out.println(usernames.stream()
                .map(username -> userRepository.findByUsername(username)
                        .orElseThrow(() -> new NotFoundException("User not found with username: " + username)))
                .collect(Collectors.toList()));
        return usernames.stream()
                .map(username -> userRepository.findByUsername(username)
                        .orElseThrow(() -> new NotFoundException("User not found with username: " + username)))
                .collect(Collectors.toList());
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    @Override
    public User createUser(User user) {
        user.setChats(new HashSet<>());
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException();
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId()).orElseThrow();
        UserMapper.INSTANCE.updatedUserFromDto(userDTO, user);
        return userRepository.save(user);
    }

    @Override
    public String deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
        userRepository.delete(user);
        return "User was deleted with id: " + id;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found by username " + username));
    }
}
