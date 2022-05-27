package com.will.transport.netty.server;

import com.will.dto.RpcRequest;
import com.will.register.DefaultServiceRegistry;
import com.will.register.ServiceRegistry;
import com.will.transport.RpcRequestHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务器端处理器，负责处理客户端请求，并执行方法返回
 *
 * @author haonan.wen
 * @createTime 2022/5/26 上午10:28
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private static RpcRequestHandler rpcRequestHandler;
    private static ServiceRegistry serviceRegistry;

    static {
        rpcRequestHandler = new RpcRequestHandler();
        serviceRegistry = new DefaultServiceRegistry();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            RpcRequest rpcRequest = (RpcRequest) msg;
            log.info(String.format("server receive msg: %s", rpcRequest));
            String interfaceName = rpcRequest.getInterfaceName();
            Object service = serviceRegistry.getService(interfaceName);
            Object result = rpcRequestHandler.handle(rpcRequest, service);
            log.info(String.format("server get result: %s", result.toString()));
            ChannelFuture channelFuture = ctx.writeAndFlush(result);
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("client catch exception");
        cause.printStackTrace();
        ctx.close();
    }
}
