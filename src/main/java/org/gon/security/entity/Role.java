package org.gon.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import org.gon.comm.SnowFlakeIdGenerator;

@Entity(name = "MEMBER_ROLE")
@Getter
public class Role {
    @Id
    @Column(name = "MEMBER_ROLE_ID")
    @SnowFlakeIdGenerator
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    @JsonIgnore
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    private RoleType roleType;

    protected Role() {
    }

    public Role(Member member, RoleType roleType) {
        this.member = member;
        this.roleType = roleType;
    }

}
