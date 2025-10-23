package org.versatile;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.versatile.domain.member.Member;
import org.versatile.domain.member.RoleType;

@Data
public class JoinMemberDto {
    private String email;
    private String pw;
    private String role;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        String encodedPw = passwordEncoder.encode(pw);
        RoleType roleType = RoleType.valueOf(role);
        return new Member(email, encodedPw, roleType);
    }
}
