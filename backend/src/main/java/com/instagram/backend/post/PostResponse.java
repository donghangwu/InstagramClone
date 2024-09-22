package com.instagram.backend.post;

import com.instagram.backend.comment.Comment;
import com.instagram.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {
    List<Post> posts;
    User user;
}
