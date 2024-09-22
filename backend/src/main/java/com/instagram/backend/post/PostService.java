package com.instagram.backend.post;

import com.instagram.backend.auth.AuthenticationResponse;
import com.instagram.backend.auth.AuthenticationResquest;
import com.instagram.backend.comment.CommentService;
import com.instagram.backend.config.JwtService;

import com.instagram.backend.following.Following;
import com.instagram.backend.following.FollowingRepository;
import com.instagram.backend.user.User;
import com.instagram.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final FollowingRepository followingRepository;


    public PostResponse getPublicPosts() {
        //return all public posts
        var posts = postRepository.findAll();

        //sort posts by created date
        Collections.sort(posts, (post2, post1) -> post1.getCreatedDate().compareTo(post2.getCreatedDate()));

        return PostResponse.builder()
               .posts(posts)
               .build();
    }

    public PostResponse createPost(PostRequest postRequest) {
        //get user email from jwt token
        String email= jwtService.extractUsername(postRequest.getJwtToken());
        //get user from database
        Optional<User> user= userRepository.findByEmail(email);
        //create post
        Post post = Post.builder()
               .content(postRequest.getContent())
                .picture(postRequest.getPicture())
                .postBy(user.get())
                .createdDate(LocalDateTime.now())
               .build();
        postRepository.save(post);
        return PostResponse.builder()
               .posts(Collections.singletonList(post))
               .build();
    }

    public PostResponse deletePost(Long postId) {
        postRepository.deleteById(postId);
        return PostResponse.builder()
               .build();
    }

    public PostResponse getUserPost(String token) {

        //remove "bearer "
        String jwtToken = token.substring(7);

        String email= jwtService.extractUsername(jwtToken);
        //find user by email
        Optional<User> user= userRepository.findByEmail(email);
        //get user id
        var userId=user.get().getId();
        //get user posts
        var posts = postRepository.findAllByPostById(userId);
        return PostResponse.builder()
               .posts(posts)
               .build();
    }

    public PostResponse getUserPostById(Long userId) {
        //get user posts
        var posts = postRepository.findAllByPostById(userId);
        //get user by id
        var user = userRepository.findById(userId);
        return PostResponse.builder()
               .posts(posts)
                .user(user.get())
                .build();
    }


    public PostResponse getFollowingPost(String token) {
        //remove "bearer "
        String jwtToken = token.substring(7);

        String email= jwtService.extractUsername(jwtToken);
        //find user by email
        Optional<User> user= userRepository.findByEmail(email);
        //get user id
        var userId=user.get().getId();
        //get user followings
        List<Following> followings = followingRepository.findAllByUserId(userId);
        //get user posts
        List<Post> posts = new ArrayList<>();
        System.out.println(followings);
        for (Following following : followings) {
            System.out.printf("following id: %d\n", following.getFollowingId());
            System.out.println(postRepository.findAllByPostById(following.getFollowingId()));
            //Collections.addAll(posts,postRepository.findAllByPostById(following.getFollowingId()));
            List<Post> followingsPosts = postRepository.findAllByPostById(following.getFollowingId());
            posts.addAll(followingsPosts);
        }

        return PostResponse.builder()
               .posts(posts)
                .user(user.get())
                .build();
    }
}
