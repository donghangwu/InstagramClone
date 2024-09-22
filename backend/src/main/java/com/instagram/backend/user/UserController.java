package com.instagram.backend.user;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> Hello() {
        return ResponseEntity.ok("Hello from secured endpoint");
    }
    @PutMapping("/update-image")
    public ResponseEntity<UserResponse> updateImage(@RequestBody UserRequest userRequest,@NonNull HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");
        final String jwtToken = authorizationHeader.split(" ")[1];

        return ResponseEntity.ok(userService.updateImage(jwtToken, userRequest));
    }





}
