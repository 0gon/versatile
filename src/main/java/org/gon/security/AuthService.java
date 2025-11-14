package org.gon.security;

import lombok.RequiredArgsConstructor;
import org.gon.comm.JwtUtil;
import org.gon.security.entity.Member;
import org.gon.security.dto.CustomUserInfoDto;
import org.gon.security.dto.LoginRequestDto;
import org.gon.security.entity.Role;
import org.gon.security.entity.RoleType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public String login(LoginRequestDto dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("이메일이 존재하지 않습니다."));

        // 암호화된 password를 디코딩한 값과 입력한 패스워드 값이 다르면 null 반환
        if(!encoder.matches(password, member.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        CustomUserInfoDto info = CustomUserInfoDto.toDto(member);

        String accessToken = jwtUtil.createAccessToken(info);
        return accessToken;
    }

    @Transactional
    public String join(LoginRequestDto request) {
        String email = request.getEmail();
        String password = encoder.encode(request.getPassword());

        Member existingMember = memberRepository.findByEmail(email).orElse(null);
        if (existingMember != null) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        Member newMember = new Member(email, password);
        Role role = new Role(newMember, RoleType.USER);
        newMember.addRole(role);

        memberRepository.save(newMember);
        return "회원가입이 완료되었습니다.";
    }

    public String grantAuthority(String email, RoleType roleType) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("이메일이 존재하지 않습니다."));
        Role role = new Role(member, roleType);
        member.addRole(role);
        memberRepository.save(member);
        return "역할이 성공적으로 업그레이드되었습니다.";
    }

    public String revokeAuthority(String email, RoleType role) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("이메일이 존재하지 않습니다."));
        member.removeRoleByType(role);
        memberRepository.save(member);
        return "역할이 성공적으로 제거되었습니다.";
    }
}
