package com.xd.pre.common.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Classname BaseException
 * @Description 自定义异常
 */
public class PreBaseException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    @Setter
    @Getter
    private String msg;

    @Setter
    @Getter
    private int code = 500;

    public PreBaseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public PreBaseException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public PreBaseException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public PreBaseException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

}
