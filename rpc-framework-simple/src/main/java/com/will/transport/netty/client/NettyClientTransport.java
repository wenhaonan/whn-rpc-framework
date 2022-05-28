package com.will.transport.netty.client;

import com.will.dto.RpcRequest;
import com.will.dto.RpcResponse;
import com.will.transport.RpcClientTransport;
import com.will.utils.checker.RpcMessageChecker;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author haonan.wen
 * @createTime 2022/5/28 下午5:31
 */
@Slf4j
public class NettyClientTransport implements RpcClientTransport {

    private final InetSocketAddress inetSocketAddress;

    public NettyClientTransport(InetSocketAddress inetSocketAddress) {
        this.inetSocketAddress = inetSocketAddress;
    }

    @Override
    public Object sendRpcRequest(RpcRequest rpcRequest) {
        try {
            Channel channel = NettyChannelProvider.get(inetSocketAddress);
            if (null != channel && channel.isActive()) {
                channel.writeAndFlush(rpcRequest).addListener(future -> {
                    if (future.isSuccess()) {
                        log.info(String.format("client send message: %s", rpcRequest.toString()));
                    } else {
                        log.error("Send failed:", future.cause());
                    }
                });
                channel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse" + rpcRequest.getRequestId());
                RpcResponse rpcResponse = channel.attr(key).get();
                RpcMessageChecker.check(rpcResponse, rpcRequest);
                return rpcResponse.getData();
            } else {
                System.exit(0);
            }
        } catch (InterruptedException e) {
            log.error("occur exception when connect server:", e);
        }
        return null;
    }
}