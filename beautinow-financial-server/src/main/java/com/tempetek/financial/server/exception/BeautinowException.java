package com.tempetek.financial.server.exception;

public class BeautinowException extends BaseException {

    public BeautinowException(ErrorType errorType) {
        super(errorType);
    }

    public BeautinowException(Integer code, String message) {
        super(code, message);
    }

    public BeautinowException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }

}
