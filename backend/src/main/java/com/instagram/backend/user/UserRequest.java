package com.instagram.backend.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor


public class UserRequest {
    public String email;
    private Long userId;
    private String img;
    private String jwtToken;
    private Long followId;
}
