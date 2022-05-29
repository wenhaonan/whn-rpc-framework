package com.will.netty;

import com.will.RpcRequestHandler;
import com.will.dto.RpcRequest;
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

    static {
        rpcRequestHandler = new RpcRequestHandler();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            log.info("thread-name: " + Thread.currentThread().getName());
            RpcRequest rpcRequest = (RpcRequest) msg;
            log.info(String.format("server receive msg: %s", rpcRequest));
            Object result = rpcRequestHandler.handle(rpcRequest);
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
