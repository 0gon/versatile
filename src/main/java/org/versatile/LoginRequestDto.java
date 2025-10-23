package org.versatile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequestDto {

    @NotNull(message = "이메일 입력은 필수입니다.")
    private String email;


    @NotNull(message = "패스워드 입력은 필수입니다.")
    private String password;
}