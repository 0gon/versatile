package org.gon.domain.member.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gon.comm.JwtUtil;
import org.gon.domain.member.service.AuthService;
import org.gon.domain.response.Response;
import org.gon.domain.member.dto.LoginRequestDto;
import org.gon.domain.member.entity.RoleType;
import org.gon.domain.response.data.TokenData;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    // 회원가입
    @PermitAll
    @PostMapping("/join")
    public Response<Void> join(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return authService.join(loginRequestDto);
    }

    // 로그인
    @PermitAll
    @PostMapping("/login")
    public Response<TokenData> login(@Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return authService.login(loginRequestDto, response);
    }


    // 권한 부여
    @PreAuthorize("hasRole('SUPER')")
    @PostMapping("/authority/grant")
    public String grantAuthority(@RequestParam String email, @RequestParam RoleType role) {
        return authService.grantAuthority(email, role);
    }

    // 권한 회수
    @PreAuthorize("hasRole('SUPER')")
    @PostMapping("/authority/revoke")
    public String revokeAuthority(@RequestParam String email, @RequestParam RoleType role) {
        return authService.revokeAuthority(email, role);
    }


}
