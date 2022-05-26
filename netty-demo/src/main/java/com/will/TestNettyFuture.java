package com.will;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author haonan.wen
 * @createTime 2022/5/22 下午2:06
 */
@Slf4j
public class TestNettyFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        ExecutorService executorService = Executors.newFixedThreadPool(2);
//        Future<Integer> future = executorService.submit(new Callable<Integer>() {
//
//            @Override
//            public Integer call() throws Exception {
//                log.debug("任务提交完成，计算结果");
//                Thread.sleep(2000);
//                return 10;
//            }
//        });
//        log.debug("任务提交完成，等待结果");
//        log.debug("异步获取结果： {}", future.get());
//        executorService.shutdown();
        NioEventLoopGroup group = new NioEventLoopGroup();
        EventLoop eventLoop = group.next();
        Future<Integer> future = eventLoop.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("任务提交完成，计算结果");
                Thread.sleep(2000);
                return 10;
            }
        });
        future.addListener(new GenericFutureListener<Future<? super Integer>>() {
            @Override
            public void operationComplete(Future<? super Integer> future) throws Exception {
                log.debug("异步获取结果： {}, {}", future.getNow(), Thread.currentThread().getName());
            }
        });
    }
}
