package com.will;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @author haonan.wen
 * @createTime 2022/5/22 下午2:21
 */
@Slf4j
public class TestNettyPromise {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1。准备 eventloop 对象
        EventLoop eventLoop = new NioEventLoopGroup().next();

        // 2. 主动创建Promise，结果的容器
        DefaultPromise<Object> promise = new DefaultPromise<>(eventLoop);

        new Thread(() -> {
            // 3. 任意一个线程执行计算，完毕后向promise填充结果
            log.debug("开始计算");
            try {
                int i = 1 / 0;
                promise.setSuccess(80);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                promise.setFailure(e);
            }
        }).start();

        log.debug("等待结果");
        log.debug("结果是： {}", promise.get());
    }
}
