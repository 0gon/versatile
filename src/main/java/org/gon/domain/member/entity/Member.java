package org.gon.domain.member.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    private RoleType role;

    protected Member() {}

    public Member(String email, String encodedPw, RoleType roleType) {
        this.email = email;
        this.password = encodedPw;
        this.role = roleType;
    }
}
