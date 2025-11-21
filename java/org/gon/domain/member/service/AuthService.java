package org.gon.domain.member.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gon.comm.JwtUtil;
import org.gon.domain.member.MemberRepository;
import org.gon.domain.response.Response;
import org.gon.domain.ResultTypeCode;
import org.gon.domain.response.data.TokenData;
import org.gon.error.ResponseException;
import org.gon.domain.member.entity.Member;
import org.gon.domain.member.dto.LoginRequestDto;
import org.gon.domain.member.entity.Role;
import org.gon.domain.member.entity.RoleType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public Response<TokenData> login(LoginRequestDto dto, HttpServletResponse response) {
        String email = dto.getEmail();
        String password = dto.getPassword();
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> {
            log.debug("not found email: {}", email);
            return new ResponseException(ResultTypeCode.FAIL, "로그인 실패");
        });

        // 암호화된 password를 디코딩한 값과 입력한 패스워드 값이 다르면 null 반환
        if (!encoder.matches(password, member.getPassword())) {
            log.debug("password not match. email: {}", email);
            throw new ResponseException(ResultTypeCode.FAIL, "로그인 실패");
        }

        String accessToken = jwtUtil.createAccessToken(member.getId());
        String refreshToken = jwtUtil.createRefreshToken(member.getId());

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) JwtUtil.REFRESH_TOKEN_EXP_TIME);
        response.addCookie(cookie);

        TokenData tokenData = new TokenData(accessToken);
        return Response.of(ResultTypeCode.SUCCESS, true, null, tokenData);
    }

    @Transactional
    public Response<Void> join(LoginRequestDto request) {
        String email = request.getEmail();
        String password = encoder.encode(request.getPassword());

        Member existingMember = memberRepository.findByEmail(email).orElse(null);
        if (existingMember != null) {
            throw new ResponseException(ResultTypeCode.ALREADY_JOIN_EMAIL, "이미 존재하는 이메일입니다.");
        }

        Member newMember = new Member(email, password);
        Role role = new Role(newMember, RoleType.USER);
        newMember.addRole(role);

        memberRepository.save(newMember);

        // 응답
        return Response.of(ResultTypeCode.SUCCESS, true,  "회원가입이 성공하였습니다.");
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
