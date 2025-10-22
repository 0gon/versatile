package org.versatile;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.versatile.domain.member.Member;

@Data
public class JoinMemberDto {
    private String email;
    private String pw;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        String encodedPw = passwordEncoder.encode(pw);
        return new Member(email, encodedPw);
    }
}
