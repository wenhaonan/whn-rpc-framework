package com.will.transport.netty;

import com.will.dto.RpcRequest;
import com.will.dto.RpcResponse;
import com.will.serializer.kyro.KryoSerializer;
import com.will.transport.RpcClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * @author haonan.wen
 * @createTime 2022/5/26 上午9:51
 */
@Slf4j
public class NettyRpcClient implements RpcClient {


    private final String host;
    private final int port;
    private static final Bootstrap b;

    public NettyRpcClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    static {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        b = new Bootstrap();
        KryoSerializer kryoSerializer = new KryoSerializer();
        b.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        /*自定义序列化编码器*/
                        // rpcResponse -> byteBuf
                        ch.pipeline().addLast(new NettyKryoDecoder(kryoSerializer, RpcResponse.class));
                        ch.pipeline().addLast(new NettyKryoEncoder(kryoSerializer, RpcRequest.class));
                        ch.pipeline().addLast(new NettyClientHandler());
                    }
                });
    }


    @Override
    public Object sendRpcRequest(RpcRequest rpcRequest) {
        try {
            ChannelFuture channelFuture = b.connect(host, port).sync();
            log.info("client connect {}", host + ":" + port);
            Channel channel = channelFuture.channel();
            if (null != channel) {
                channel.writeAndFlush(rpcRequest).addListener(future -> {
                    if (future.isSuccess()) {
                        log.info(String.format("client send message: %s", rpcRequest.toString()));
                    } else {
                        log.error("Send failed:", future.cause());
                    }
                });
                channel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
                RpcResponse rpcResponse = channel.attr(key).get();
                return rpcResponse.getData();
            }
        } catch (InterruptedException e) {
            log.error("occur exception when connect server:", e);
        }
        return null;
    }
}
