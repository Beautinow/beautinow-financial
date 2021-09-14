package com.tempetek.financial.server.exception;

public interface ErrorType {

    Integer getCode();

    String getMessage();

    String getData();

}
