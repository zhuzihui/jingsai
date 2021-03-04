package com.xd.pre.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Classname ValidateCodeException
 * @Description TODO
 */
public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = 5022575393500654459L;

    public ValidateCodeException(String message) {
        super(message);
    }
}
