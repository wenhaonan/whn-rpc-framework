package com.will.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author haonan.wen
 * @createTime 2022/5/19 下午2:50
 */
@Getter
@ToString
@AllArgsConstructor
public enum RpcErrorMessageEnum {

    SERVICE_INVOCATION_FAILURE("服务调用失败"),
    SERVICE_CAN_NOT_BE_FOUND("没有找到指定的服务"),
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("注册的服务没有实现任何接口");

    private final String message;

}
