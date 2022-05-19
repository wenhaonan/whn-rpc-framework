package com.will.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RpcRequest implements Serializable {

    private static final long serialVersionUID = -4763040545272377034L;

    private String interfaceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramTypes;
}
