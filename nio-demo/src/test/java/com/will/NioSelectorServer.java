package com.will;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

/**
 * @author haonan.wen
 * @createTime 2022/5/19 下午7:00
 */
@Slf4j
public class NioSelectorServer {
    public static void main(String[] args) throws IOException {
        // 1.创建selector， 管理多个 channel
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        // 2.建立selector和channel的联系（注册）
        // selectionKey 将来事件发生后，可以知道事件在那个channel
        SelectionKey sscKey = ssc.register(selector, 0, null);
        // key只关注accept事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));

        while (true) {
            // 3. select方法
            selector.select();
            
            // 4. 处理事件 selectedKeys 内部包含了所有发生的事件
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
        }
    }
}
