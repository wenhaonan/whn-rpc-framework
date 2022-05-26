package com.will.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @author haonan.wen
 * @createTime 2022/5/21 下午5:43
 */
public class WriteServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector selector = Selector.open();
        SelectionKey ssckey = ssc.register(selector, SelectionKey.OP_ACCEPT, null);
        ssc.bind(new InetSocketAddress("localhost", 8080));

        while (true) {
            System.out.println("循环了");
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey sk = iterator.next();
                iterator.remove();
                if (sk.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    SelectionKey sckey = sc.register(selector, 0, null);
                    sckey.interestOps(SelectionKey.OP_READ);
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < 30000000; i++) {
                        sb.append("a");
                    }
                    ByteBuffer buffer = Charset.defaultCharset().encode(sb.toString());

                    int write = sc.write(buffer);
                    System.out.println(write);

                    if (buffer.hasRemaining()) {
                        sckey.interestOps(sckey.interestOps() + SelectionKey.OP_WRITE);
                        sckey.attach(buffer);
                    }
                } else if (sk.isWritable()) {
                    ByteBuffer buffer = (ByteBuffer) sk.attachment();
                    SocketChannel sc = (SocketChannel) sk.channel();

                    int write = sc.write(buffer);
                    System.out.println(write);

                    if (!buffer.hasRemaining()) {
                        sk.attach(null);
                        sk.interestOps(sk.interestOps() - SelectionKey.OP_WRITE);
                    }
                }
            }
        }
    }
}
