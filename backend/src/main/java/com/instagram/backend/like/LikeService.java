package com.instagram.backend.like;


import com.instagram.backend.config.JwtService;
import com.instagram.backend.post.Post;
import com.instagram.backend.post.PostRepository;
import com.instagram.backend.user.User;
import com.instagram.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    public LikeResponse likePost(LikeRequest likeRequest, String token) {

        //remove "bearer "
        String jwtToken = token.substring(7);
        //extract email from jwt token
        String userId = jwtService.extractUsername(jwtToken);
        //get post by id
        Post post = postRepository.findById(likeRequest.getPostId()).orElse(null);
        //get user by email
        User user = userRepository.findByEmail(userId).get();
        //check if user has already liked the post
        if (likeRepository.existsByUserAndPost(user, post)) {
            return new LikeResponse();
        }
        else{
            Like like = new Like().builder()
                   .post(post)
                   .user(user)
                   .build();
            likeRepository.save(like);
            Post newPost = postRepository.findById(likeRequest.getPostId()).orElse(null);
            return new LikeResponse().builder()
                   .post(newPost)
                   .build();
        }


    }

    public LikeResponse unlikePost(LikeRequest followingRequest, String token) {
        //remove "bearer "
        String jwtToken = token.substring(7);
        //extract email from jwt token
        String userId = jwtService.extractUsername(jwtToken);
        //get post by id
        Post post = postRepository.findById(followingRequest.getPostId()).orElse(null);
        //get user by email
        User user = userRepository.findByEmail(userId).get();
        //find the like object
        Like like = likeRepository.findByUserAndPost(user, post);
        //delete the like object
        likeRepository.delete(like);
        Post newPost = postRepository.findById(followingRequest.getPostId()).orElse(null);
        return new LikeResponse().builder()
               .post(newPost)
                .build();
        }
}
