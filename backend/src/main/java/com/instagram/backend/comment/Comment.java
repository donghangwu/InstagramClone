package com.instagram.backend.comment;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.instagram.backend.post.Post;
import com.instagram.backend.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="_comments")
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private String content;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User postBy;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                // Avoid calling toString on post to prevent recursion
                ", postId=" + (post != null ? post.getId() : null) +
                '}';
    }
}
