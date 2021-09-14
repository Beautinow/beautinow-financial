package com.tempetek.financial.server.exception;

public class BaseException extends RuntimeException {

    private Integer code;

    public BaseException(ErrorType errorType) {
        super(errorType.getMessage());
        this.code = errorType.getCode();
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }

}
