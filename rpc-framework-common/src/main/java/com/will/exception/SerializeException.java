package com.will.exception;

/**
 * @author haonan.wen
 * @createTime 2022/5/19 下午2:54
 */
public class SerializeException extends RuntimeException {
    public SerializeException(String message) {
        super(message);
    }
}
