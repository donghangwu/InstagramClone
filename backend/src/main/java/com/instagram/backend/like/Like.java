package com.instagram.backend.like;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.instagram.backend.post.Post;
import com.instagram.backend.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="_like")
public class Like {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne

    @JoinColumn(name="user_id")
    private User user;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    @Override
    public String toString() {
        return "Like [id=" + id +   "]";
    }

}
