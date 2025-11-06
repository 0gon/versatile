package org.gon.domain.member.dto;

import lombok.*;
import org.gon.domain.member.entity.Member;
import org.gon.domain.member.entity.RoleType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomUserInfoDto {
    private Long memberId;

    private String email;

    private String password;

    private RoleType role;

    public static CustomUserInfoDto toDto(Member member) {
        return new CustomUserInfoDto(
                member.getId(),
                member.getEmail(),
                member.getPassword(),
                member.getRole()
        );
    }
}