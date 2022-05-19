package com.will;

import com.will.enums.RpcErrorMessageEnum;
import com.will.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class RpcServer {
    private ExecutorService threadPool;
    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    public RpcServer() {
        // 线程池参数
        int corePoolSize = 10;
        int maximumPoolSizeSize = 100;
        long keepAliveTime = 1;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        this.threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSizeSize, keepAliveTime, TimeUnit.MINUTES, workQueue, threadFactory);
    }

    /**
     * 服务端自动注册服务
     * TODO 修改为注解扫描
     */
    public void register(Object service, int port) {
        if (null == service) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_NULL);
        }
        try (ServerSocket socketServer = new ServerSocket(port);) {
            logger.info("server starts");
            logger.info("service registered: " + service.getClass().getName());
            Socket socket;
            while ((socket = socketServer.accept()) != null) {
                logger.info("client connected");
                threadPool.execute(new ClientMessageHandlerThread(socket, service));
            }
        } catch (IOException e) {
            logger.error("occur IOException", e);
        }
    }

}
