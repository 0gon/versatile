package org.versatile.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.versatile.domain.member.Member;
import org.versatile.domain.member.RoleType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserInfoDto {
    private Long memberId;

    private String email;

    private String password;

    private RoleType role;



    public static CustomUserInfoDto toMember(Member member) {
        return new CustomUserInfoDto(member.getId(), member.getEmail(), member.getPassword(), member.getRole());
    }
}
