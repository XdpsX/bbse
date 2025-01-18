package com.bbse.identity.exception;

import com.bbse.identity.util.MessagesUtils;

public class DuplicateException extends RuntimeException {

    private final String message;

    public DuplicateException(String errorCode, Object... var2) {
        this.message = MessagesUtils.getMessage(errorCode, var2);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
