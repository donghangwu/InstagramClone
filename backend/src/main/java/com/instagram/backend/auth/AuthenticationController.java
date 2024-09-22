package com.instagram.backend.auth;


import com.jpomykala.springhoc.cors.EnableCORS;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthenticationController {
    private final AuthenticationService authenticationService;


    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request)
    {


        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationResquest request)
    {

        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
