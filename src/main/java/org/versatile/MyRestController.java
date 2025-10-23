package org.versatile;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.versatile.domain.member.JpaMemberRepository;
import org.versatile.domain.member.Member;

@RestController
@RequiredArgsConstructor
public class MyRestController {

    private final PasswordEncoder passwordEncoder;
    private final JpaMemberRepository memberRepo;
    private final AuthService authService;

    @GetMapping("/")
    public String temp() {
        System.out.println("w11q66qw");
        return "success2";
    }

    @GetMapping("/test")
    public String test() {
        return "success auth";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto request) {
        String token = this.authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @PostMapping("/members")
    public String createMember(JoinMemberDto memberDto) {
        Member entity = memberDto.toEntity(passwordEncoder);
        memberRepo.save(entity);
        return "member created";
    }

}
