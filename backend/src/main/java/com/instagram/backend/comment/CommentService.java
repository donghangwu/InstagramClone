package com.instagram.backend.comment;

import com.instagram.backend.config.JwtService;
import com.instagram.backend.post.Post;
import com.instagram.backend.post.PostRepository;
import com.instagram.backend.post.PostRequest;
import com.instagram.backend.user.User;
import com.instagram.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;



    public CommentResponse createComment(CommentRequest commentRequest,String token) {

        //find user email in jwt token
        String jwtToken = token.substring(7);
        String email = jwtService.extractUsername(jwtToken);
        Optional<User> user = userRepository.findByEmail(email);
        Optional<Post> post = postRepository.findById(commentRequest.getPostId());

        //discard user's password
        user.get().setPassword(null);
        post.get().getPostBy().setPassword(null);
        //build comment object
        Comment comment = Comment.builder()
                .post(post.get())//link comment to post
                .postBy(user.get())//link comment to user who created it
                .content(commentRequest.getContent())
                .timestamp(LocalDateTime.now())
                .build();
        commentRepository.save(comment);


        return CommentResponse.builder()
                .comment(comment)
                .build();
    }

    public CommentResponse deleteComment(CommentRequest comment) {
        commentRepository.deleteById(comment.getCommentId());
        return CommentResponse.builder()
                .build();

    }
}
