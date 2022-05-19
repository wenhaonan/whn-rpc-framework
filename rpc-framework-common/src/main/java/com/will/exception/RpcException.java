package com.will.exception;

import com.will.enums.RpcErrorMessageEnum;

/**
 * @author haonan.wen
 * @createTime 2022/5/19 下午2:54
 */
public class RpcException extends RuntimeException {

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcErrorMessageEnum rpcErrorMessageEnum) {
        super(rpcErrorMessageEnum.getMessage());
    }

    public RpcException(RpcErrorMessageEnum rpcErrorMessageEnum, String detail) {
        super(rpcErrorMessageEnum.getMessage() + ":" + detail);
    }
}
