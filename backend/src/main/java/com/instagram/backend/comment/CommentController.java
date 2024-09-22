package com.instagram.backend.comment;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/createcomment")
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest comment,@RequestHeader("Authorization") String token) {
        //System.out.println(comment.toString());
        return ResponseEntity.ok(commentService.createComment(comment,token));
    }
    @PostMapping("/deletecomment")
    public ResponseEntity<CommentResponse> deleteComment(@RequestBody CommentRequest comment) {

        return ResponseEntity.ok(commentService.deleteComment(comment));
    }

}
