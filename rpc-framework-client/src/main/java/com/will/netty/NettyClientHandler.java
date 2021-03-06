package com.will.netty;

import com.will.dto.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义netty客户端channelHandler， 处理服务器发过来的数据
 *
 * @author haonan.wen
 * @createTime 2022/5/26 上午10:28
 */
@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取服务器传输数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            RpcResponse rpcResponse = (RpcResponse) msg;
            log.info(String.format("client receive msg: %s", rpcResponse));
            // 声明一个AttributeKey对象
            AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse" + rpcResponse.getRequestId());
            // 将服务端的返回结果保存到 AttributeMap上，可以看作是一个Channel的共享数据原
            ctx.channel().attr(key).set(rpcResponse);
            ctx.channel().close();
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
