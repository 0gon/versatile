package org.gon.error;

import lombok.Getter;
import org.gon.domain.ResultTypeCode;

@Getter
public class ResponseException extends RuntimeException {

    private final ResultTypeCode resultTypeCode;

    public ResponseException(ResultTypeCode resultTypeCode, String message) {
        super(message);
        this.resultTypeCode = resultTypeCode;
    }
}
