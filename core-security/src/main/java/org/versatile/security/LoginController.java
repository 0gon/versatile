package org.versatile.security;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.versatile.domain.member.User;
import org.versatile.domain.member.UsersRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



// @RestController
@Controller
@RequiredArgsConstructor
public class LoginController {
    
    // private final AuthenticationManager authenticationManager;

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // @PostMapping("/login")
    // public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {
    //     Authentication authenticationRequest =
    //         UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());

    //     Authentication authenticationResponse = 
    //         this.authenticationManager.authenticate(authenticationRequest);
            
    //     return null;
    // }


    public record LoginRequest(String username, String password) {}

    
    
    
    
}
