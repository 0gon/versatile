package org.versatile;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.versatile.domain.member.JpaMemberRepo;

@RestController
@RequiredArgsConstructor
public class MyRestController {

    private final PasswordEncoder passwordEncoder;
    private final JpaMemberRepo memberRepo;

    @GetMapping
    public String temp() {
        return "success";
    }

    @PostMapping("/members")
    public String createMember(JoinMemberDto memberDto) {
        memberRepo.save(memberDto.toEntity(passwordEncoder));
        return "member created";
    }

}
