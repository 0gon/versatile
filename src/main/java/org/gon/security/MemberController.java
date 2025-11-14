package org.gon.security;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gon.security.dto.LoginRequestDto;
import org.gon.security.entity.RoleType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final AuthService authService;

    // 회원가입
    @PermitAll
    @PostMapping("/join")
    public String join(@Valid @RequestBody LoginRequestDto request) {
        return this.authService.join(request);
    }

    // 로그인
    @PermitAll
    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequestDto request) {
        String token = this.authService.login(request);
        return token;
    }

    // 권한 부여
    @PreAuthorize("hasRole('SUPER')")
    @PostMapping("/authority/grant")
    public String grantAuthority(@RequestParam String email, @RequestParam RoleType role) {
        return this.authService.grantAuthority(email, role);
    }

    // 권한 회수
    @PreAuthorize("hasRole('SUPER')")
    @PostMapping("/authority/revoke")
    public String revokeAuthority(@RequestParam String email, @RequestParam RoleType role) {
        return this.authService.revokeAuthority(email, role);
    }


}
