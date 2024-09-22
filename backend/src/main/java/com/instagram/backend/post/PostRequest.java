package com.instagram.backend.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequest {
    private String content;
    private String picture;
    private String jwtToken;
    private Long postId;

}
