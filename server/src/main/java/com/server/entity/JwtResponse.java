package com.server.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@AllArgsConstructor
public class JwtResponse {

    private String accessToken;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
    private final String type = "Bearer";
}
