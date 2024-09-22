package com.instagram.backend.post;

import com.instagram.backend.comment.Comment;
import com.instagram.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByPostById(Long userId);
    // public Post updatePostCommentById(Long postId, Comment comment);
}
