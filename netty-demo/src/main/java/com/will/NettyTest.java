package com.will;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author haonan.wen
 * @createTime 2022/5/21 下午11:48
 */
@Slf4j
public class NettyTest {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup(2);


        group.next().submit(() -> {
            log.debug("hello");
        });

        group.next().scheduleAtFixedRate(() -> {
            log.debug("hello");
        }, 0, 1, TimeUnit.SECONDS);

    }
}
