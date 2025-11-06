package org.gon.domain.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.gon.comm.HibernateIdGenerator;
import org.gon.comm.MyIdGenerator;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.IdGeneratorType;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.id.IdentifierGenerator;

@Entity
@Getter
public class Member {

    @Id
    @MyIdGenerator
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
