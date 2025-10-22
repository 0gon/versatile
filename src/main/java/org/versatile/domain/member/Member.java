package org.versatile.domain.member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String encodedPw;


    public Member(String email, String encodedPw) {
        this.email = email;
        this.encodedPw = encodedPw;
    }
}
