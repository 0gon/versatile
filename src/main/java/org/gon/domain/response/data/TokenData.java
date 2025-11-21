package org.gon.domain.response.data;

import lombok.Getter;

@Getter
public class TokenData {
    String token;

    public TokenData(String token) {
        this.token = token;
    }
}
