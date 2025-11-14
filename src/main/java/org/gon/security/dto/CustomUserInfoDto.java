package org.gon.security.dto;

import lombok.*;
import org.gon.security.entity.Member;
import org.gon.security.entity.Role;
import org.gon.security.entity.RoleType;

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