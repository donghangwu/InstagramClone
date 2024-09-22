package com.instagram.backend.post;

import com.instagram.backend.auth.AuthenticationResponse;
import com.instagram.backend.auth.AuthenticationResquest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    @PostMapping("/createpost")
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest)
    {


        return ResponseEntity.ok(postService.createPost(postRequest));
    }



    @GetMapping("/userpost")
    public ResponseEntity<PostResponse> userPost(@RequestHeader("Authorization") String token)
    {

        return ResponseEntity.ok(postService.getUserPost(token));
    }
    @GetMapping("/followingpost")
    public ResponseEntity<PostResponse> followingPost(@RequestHeader("Authorization") String token)
    {

        return ResponseEntity.ok(postService.getFollowingPost(token));
    }

    @GetMapping("/posts/{userId}")
    public ResponseEntity<PostResponse> userPost(@PathVariable("userId") Long userId)
    {
        return ResponseEntity.ok(postService.getUserPostById(userId));
    }


    @GetMapping("/publicpost")
    public ResponseEntity<PostResponse> allPublicPosts()
    {

        return ResponseEntity.ok(postService.getPublicPosts());
    }
    @DeleteMapping("/deletepost/{postId}")
    public ResponseEntity<PostResponse> deletePost(@PathVariable("postId") Long postId)
    {
        return ResponseEntity.ok(postService.deletePost(postId));
    }

}
