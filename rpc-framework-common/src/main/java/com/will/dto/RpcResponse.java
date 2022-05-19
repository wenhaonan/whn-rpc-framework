package com.will.dto;

import com.will.enums.RpcResponseCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @author haonan.wen
 * @createTime 2022/5/19 下午2:37
 */
@Data
public class RpcResponse<T> implements Serializable {

    private static final long serialVersionUID = 3944133975785226662L;

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    public static <T> RpcResponse<T> success(T data) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setCode(RpcResponseCode.SUCCESS.getCode());
        if (null != data) {
            response.setData(data);
        }
        return response;
    }

    public static <T> RpcResponse<T> fail(RpcResponseCode rpcResponseCode) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setCode(rpcResponseCode.getCode());
        response.setMessage(rpcResponseCode.getMessage());
        return response;
    }
}
