package org.versatile;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.versatile.config.CustomUserInfoDto;
import org.versatile.config.JwtUtil;
import org.versatile.domain.member.JpaMemberRepository;
import org.versatile.domain.member.Member;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final JpaMemberRepository memberRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public String login(LoginRequestDto dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();
        Member member = memberRepository.findMemberByEmail(email);
        if(member == null) {
            throw new UsernameNotFoundException("이메일이 존재하지 않습니다.");
        }

        // 암호화된 password를 디코딩한 값과 입력한 패스워드 값이 다르면 null 반환
        if(!encoder.matches(password, member.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        CustomUserInfoDto info = CustomUserInfoDto.toMember(member);

        String accessToken = jwtUtil.createAccessToken(info);
        return accessToken;
    }
}