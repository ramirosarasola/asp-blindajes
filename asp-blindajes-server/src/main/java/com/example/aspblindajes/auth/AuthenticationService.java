package com.example.aspblindajes.auth;

import com.example.aspblindajes.config.JwtService;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.User;
import com.example.aspblindajes.model.Role;
import com.example.aspblindajes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request){
        User user = User.builder()
                .name(request.getName())
                .isEnabled(true)
                .lastname(request.getLastName())
                .username(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    };

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws ResourceNotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findUserByUsername(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid User"));
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public User getUserInfo(String username) throws ResourceNotFoundException {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if(userOptional.isEmpty()){
            throw new ResourceNotFoundException("The user doesn't exist");
        }
        return userOptional.get();
    }
}
