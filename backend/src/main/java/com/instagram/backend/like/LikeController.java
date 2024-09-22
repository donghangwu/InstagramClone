package com.instagram.backend.like;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likePost")

public class LikeController {
    private final LikeService likeService;

    @PutMapping("/like")
    public ResponseEntity<LikeResponse> likePost(@RequestBody LikeRequest followingRequest,@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(likeService.likePost(followingRequest, token));
    }

    @PutMapping("/unlike")
    public ResponseEntity<LikeResponse> unlikePost(@RequestBody LikeRequest followingRequest,@RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(likeService.unlikePost(followingRequest, token));
    }

}
