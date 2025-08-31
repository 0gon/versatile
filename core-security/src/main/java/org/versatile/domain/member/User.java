package org.versatile.domain.member;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Getter;

@Getter
public class User implements UserDetails, CredentialsContainer {
   private String username;
   private String password;
   private UserRole role;
   private UserGrade grade;
   private LocalDateTime createdAt;
   private LocalDateTime modifiedAt;
   private LocalDateTime deletedAt;
   private boolean enabled;

   public void encodePassword(PasswordEncoder passwordEncoder, String rawPassword) {
      this.password = passwordEncoder.encode(rawPassword);
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return List.of(new SimpleGrantedAuthority("ROLE_" + String.valueOf(role)));
   }

   @Override
   public String getUsername() {
      return username;
   }

   @Override
   public boolean isAccountNonExpired() {
      // true -> 계정 만료되지 않았음
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      // true -> 계정 잠금되지 않음
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      // true -> 패스워드 만료 되지 않음
      return true;
   }

   @Override
   public boolean isEnabled() {
      // ture -> 계정 사용 가능
      return true;
   }

   @Override
   public void eraseCredentials() {
      this.password = null;
   }
}