package com.will;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static com.will.ByteBufferUtil.debugRead;

/**
 * @author haonan.wen
 * @createTime 2022/5/19 下午7:00
 */
@Slf4j
public class NioServer {
    public static void main(String[] args) throws IOException {
        // 使用nio理解阻塞模式, 单线程

        // 1.创建了服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 2.绑定一个监听端口
        ssc.bind(new InetSocketAddress(8080));
        // 连接集合
        List<SocketChannel> channelList = new ArrayList<>();

        ByteBuffer buffer = ByteBuffer.allocate(16);
        while (true) {
            log.debug("connecting...");
            // 3.accept 建立客户端连接
            SocketChannel accept = ssc.accept(); // 处于阻塞状态
            log.debug("connected... {}", accept);
            channelList.add(accept);

            for (SocketChannel channel : channelList) {
                log.debug("before read...", channel);
                channel.read(buffer); // 阻塞，没有数据一直等待
                buffer.flip();
                debugRead(buffer);
                buffer.clear();
                log.debug("after read...", channel);
            }
        }
    }
}
