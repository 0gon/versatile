package org.versatile.config;

import org.springframework.http.HttpStatus;

public class TokenException extends CustomException {

    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }

}
