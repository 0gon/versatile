package org.gon;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gon.domain.member.dto.LoginRequestDto;
import org.gon.security.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestApiController {

    private final AuthService authService;

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @GetMapping("/auth")
    public ResponseEntity<String> authTest() {
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping("/login")
    public ResponseEntity<String> getMemberProfile(
            @Valid @RequestBody LoginRequestDto request
    ) {
        String token = this.authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinMember(
            @Valid @RequestBody LoginRequestDto request
    ) {
        String result = this.authService.join(request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}