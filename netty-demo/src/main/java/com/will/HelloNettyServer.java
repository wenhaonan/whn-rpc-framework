package com.will;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author haonan.wen
 * @createTime 2022/5/21 下午10:08
 */
public class HelloNettyServer {
    public static void main(String[] args) {
        // 1. 启动器，负责组装netty， 启动服务器
        new ServerBootstrap()
                // 2. BossEventLoop WorkerEventLoop（selector, thread）
                .group(new NioEventLoopGroup())
                // 3. 选择服务器的 ServerSocketChannel的实现
                .channel(NioServerSocketChannel.class)
                // 4. boss负责处理连接，worker处理读写， 决定了worker将来能做的事情
                .childHandler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println(msg);
                            }
                        });
                    }
                }).bind(8080);
    }
}
