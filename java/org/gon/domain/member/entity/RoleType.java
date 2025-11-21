package org.gon.domain.member.entity;

import lombok.ToString;

public enum RoleType {

    SUPER("SUPER"),
    ADMIN("ADMIN"),
    USER("USER");

    private final String roleName;

    RoleType(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
