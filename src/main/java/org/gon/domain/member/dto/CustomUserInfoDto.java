package org.gon.domain.member.dto;

import lombok.*;
import org.gon.domain.member.entity.Member;
import org.gon.domain.member.entity.Role;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomUserInfoDto {
    private Long memberId;

    private String email;

    private String password;

    private List<Role> roles;

    public static CustomUserInfoDto toDto(Member member) {
        return new CustomUserInfoDto(
                member.getId(),
                member.getEmail(),
                member.getPassword(),
                member.getRoles()
        );
    }
}