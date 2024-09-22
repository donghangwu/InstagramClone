package com.instagram.backend.follower;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    Follower findByUserIdAndFollowerId(Long userId, Long followerId);
}
