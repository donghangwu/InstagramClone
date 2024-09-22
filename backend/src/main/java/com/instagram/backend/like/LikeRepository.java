package com.instagram.backend.like;

import com.instagram.backend.post.Post;
import com.instagram.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserAndPost(User user, Post post);

    Like findByUserAndPost(User user, Post post);
}

