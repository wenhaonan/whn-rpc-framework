package com.will.netty;

import com.will.dto.RpcRequest;
import com.will.dto.RpcResponse;
import com.will.serializer.codec.NettyKryoDecoder;
import com.will.serializer.codec.NettyKryoEncoder;
import com.will.serializer.kyro.KryoSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author haonan.wen
 * @createTime 2022/5/26 上午9:51
 */
@Slf4j
public class NettyClient {

    private static final Bootstrap b;


    static {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        b = new Bootstrap();
        KryoSerializer kryoSerializer = new KryoSerializer();
        b.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                // 连接超时时间，超过则连接失败
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                // 是否开启tcp心跳
                .option(ChannelOption.SO_KEEPALIVE, true)
                // tcp默认开启了nagle算法，尽可能发送大数据快，减少网络延迟
                .option(ChannelOption.TCP_NODELAY, true)
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

    private NettyClient() {
    }

    public static void close() {
        log.info("call close method");
    }

    public static Bootstrap initializeBootstrap() {
        return b;
    }
}
