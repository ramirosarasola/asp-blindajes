package com.example.aspblindajes.auth;

import com.example.aspblindajes.auth.AuthenticationRequest;
import com.example.aspblindajes.auth.AuthenticationResponse;
import com.example.aspblindajes.auth.RegisterRequest;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) throws ResourceNotFoundException {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }


}
