package org.versatile.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.versatile.domain.member.User;
import org.versatile.domain.member.UsersRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MyRestController {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/test")
    public String test() {
        return "test";
    }

    @PostMapping("/auth")
    public String auth() {
        return "auth";
    }
    
    

    @PostMapping("/join")
    public int join(@RequestBody User user) {
        user.encodePassword(passwordEncoder, user.getPassword());
        return usersRepository.save(user);
    }


}
