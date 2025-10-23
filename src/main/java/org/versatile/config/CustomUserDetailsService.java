package org.versatile.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.versatile.domain.member.JpaMemberRepository;
import org.versatile.domain.member.Member;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private final JpaMemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.parseLong(id)).orElseThrow(() -> new UsernameNotFoundException("해당하는 유저가 없습니다"));
        CustomUserInfoDto dto = CustomUserInfoDto.toMember(member);
        return new CustomUserDetails(dto);
    }

}
