package com.will.transport;

import com.will.dto.RpcRequest;

/**
 * @author haonan.wen
 * @createTime 2022/5/25 下午8:01
 */
public interface RpcClient {
    Object sendRpcRequest(RpcRequest rpcRequest);
}
