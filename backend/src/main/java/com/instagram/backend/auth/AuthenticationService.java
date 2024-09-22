package com.instagram.backend.auth;


import com.instagram.backend.config.JwtService;
import com.instagram.backend.user.Role;
import com.instagram.backend.user.User;
import com.instagram.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        //check if user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return AuthenticationResponse.builder().error("Error: Email already exists").build();
        }
        var user = User.builder()
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .img("https://res.cloudinary.com/dwu20/image/upload/v1597790821/defualt2_xli5go.webp")//default image
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).success("Successful created user").build();
    }

    public AuthenticationResponse authenticate(AuthenticationResquest request) {

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),request.password
                ));
        //if user and password is correct, need to generate the token and send it  back
        var user =userRepository.findByEmail(request.getEmail()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).user(user).success("Successful authentication").build();


        }catch (BadCredentialsException e) {
            //throw new BadCredentialsException("Invalid email or password");
            return AuthenticationResponse.builder().error("Error: Invalid email or password").build();

        }



    }
}
