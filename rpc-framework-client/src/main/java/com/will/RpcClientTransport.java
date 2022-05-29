package com.will;

import com.will.dto.RpcRequest;

/**
 * @author haonan.wen
 * @createTime 2022/5/25 下午8:01
 */
public interface RpcClientTransport {
    Object sendRpcRequest(RpcRequest rpcRequest);
}
