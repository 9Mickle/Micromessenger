package com.server.controller;

import com.server.util.JwtUtil;
import com.server.entity.request.AuthenticationRequest;
import com.server.entity.JwtResponse;
import com.server.entity.User;
import com.server.repository.RoleRepository;
import com.server.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/auth")
@CrossOrigin()
@AllArgsConstructor
public class AuthController {

    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@RequestBody AuthenticationRequest authRequest) {
        if (userService.findByUsername(authRequest.getUsername()) != null) {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
            String jwt = JwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(jwt, authRequest.getUsername(), userDetails.getAuthorities()));
        } else {
            return new ResponseEntity<>("User not Found", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody User user) {
        // Ставим по умолчанию.
        com.server.entity.Role userRole = roleRepository.findByRoleName("ROLE_USER");
        user.setRoles(Stream.of(userRole).collect(Collectors.toSet()));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.createUser(user);
        return ResponseEntity.ok("User registered successfully!");
    }
}
