package com.instagram.backend.following;

import com.instagram.backend.config.JwtService;
import com.instagram.backend.follower.Follower;
import com.instagram.backend.follower.FollowerRepository;
import com.instagram.backend.user.User;
import com.instagram.backend.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/following")
public class FollowingController {
    private final FollowingRepository followingRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final FollowerRepository followerRepository;
    private final FollowingService followingService;
    @PostMapping("/follow")
    public ResponseEntity<FollowingResponse> follow(@RequestBody FollowingRequest followingRequest, @NonNull HttpServletRequest request) {

        return ResponseEntity.ok(followingService.follow(followingRequest, request));

    }
    @PostMapping("/unfollow")
    public ResponseEntity<FollowingResponse> unfollow(@RequestBody FollowingRequest followingRequest, @NonNull HttpServletRequest request) {

        return ResponseEntity.ok(followingService.unfollow(followingRequest, request));
    }


}
