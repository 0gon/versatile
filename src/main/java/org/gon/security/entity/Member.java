package org.gon.security.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.gon.comm.SnowFlakeIdGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    @SnowFlakeIdGenerator
    private Long id;
    private String email;
    private String password;

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Role> roles = new ArrayList<>();

    protected Member() {
    }

    public Member(String email, String encodedPw, List<Role> roles) {
        this.email = email;
        this.password = encodedPw;
        this.roles = roles;
    }

    public Member(String email, String encodedPw) {
        this.email = email;
        this.password = encodedPw;
    }

    public void addRole(Role role) {
        if(hasRole(role.getRoleType())) {
            throw new IllegalArgumentException("이미 존재하는 역할입니다.");
        }
        this.roles.add(role);
    }

    public void removeRoleByType(RoleType roleType) {
        boolean b = this.roles.removeIf(role -> role.getRoleType() == roleType);
        if(!b) {
            throw new IllegalArgumentException("존재하지 않는 역할입니다.");
        }
    }

    public boolean hasRole(RoleType roleType) {
        return this.roles.stream().anyMatch(role -> role.getRoleType() == roleType);
    }
}
