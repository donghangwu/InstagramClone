package com.instagram.backend.user;

import com.instagram.backend.config.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserResponse updateImage(String jwtToken, UserRequest userRequest) {

        // Extract email from JWT token
        String email= jwtService.extractUsername(jwtToken);
        // Find user by email
        Optional<User> user = userRepository.findByEmail(email);
        // Update user image
        user.get().setImg(userRequest.getImg());
        User updatedUser = userRepository.save(user.get());

        return UserResponse.builder()
               .id(updatedUser.getId())
               .email(updatedUser.getEmail())
               .img(updatedUser.getImg())
                .build();

    }

}
