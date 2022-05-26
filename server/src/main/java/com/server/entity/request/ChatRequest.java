package com.server.entity.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@AllArgsConstructor
public class ChatRequest {
    @NotEmpty
    private String title;
    @NotEmpty
    private List<String> usernames;
}
