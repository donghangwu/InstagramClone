package com.instagram.backend.following;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowingRepository extends JpaRepository<Following, Long> {
    Following findByUserIdAndFollowingId(Long userId, Long followingId);

    List<Following> findAllByUserId(Long userId);
}
