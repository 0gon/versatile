package org.gon.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.gon.domain.member.entity.Member;
import org.gon.domain.member.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class MemberDetails implements UserDetails {

    private final Long id;
    private final String email;
    private final String password;
    private final List<Role> roles;

    public static MemberDetails from(Member member) {
        return new MemberDetails(
                member.getId(),
                member.getEmail(),
                member.getPassword(),
                member.getRoles()
        );
    }

    /**
     * 아이디 대신에 맴버의 식별자를 사용.
     * jwt에서도 subject를 맴버의 id로 사용할 것.
     */
    @Override
    public String getUsername() {
        return String.valueOf(this.id);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleType().getRoleName()))
                .collect(Collectors.toList());
    }

    // 미사용 ==========================================
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



}