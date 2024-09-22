package com.instagram.backend.post;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.instagram.backend.comment.Comment;
import com.instagram.backend.like.Like;
import com.instagram.backend.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Post {
    @Id
    @GeneratedValue
    private Long id;
    private String content;
    private String picture="https://res.cloudinary.com/dwu20/image/upload/v1597790821/defualt2_xli5go.webp";
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User postBy;

    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<Comment> comments;
//
    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<Like> likeBy;

}
