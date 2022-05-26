package com.will.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author haonan.wen
 * @createTime 2022/5/21 下午5:49
 */
public class ReadClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);
        int count = 0;
        while (true) {
            count += sc.read(byteBuffer);
            System.out.println(count);
            byteBuffer.clear();
        }
    }
}
