package org.gon.domain.response;

import lombok.Getter;
import org.gon.domain.ResultTypeCode;

@Getter
public class Response<T> {
    ResultTypeCode resultTypeCode;
    boolean success;
    String message;
    T data;


    public Response(ResultTypeCode resultTypeCode, boolean success, String message, T data) {
        this.resultTypeCode = resultTypeCode;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Response(ResultTypeCode resultTypeCode, boolean success, String message) {
        this(resultTypeCode, success, message, null);
    }

    public static <T> Response<T> of(ResultTypeCode resultTypeCode, boolean success, String message, T data) {
        return new Response<>(resultTypeCode, success, message, data);
    }

    public static Response<Void> of(ResultTypeCode resultTypeCode, boolean success, String message) {
        return new Response<>(resultTypeCode, success, message);
    }
}
