package com.will.remoting.socket;

import com.will.register.ServiceRegistry;
import com.will.remoting.RpcRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class RpcServer {
    /**
     * 线程池参数
     */
    private static final int CORE_POOL_SIZE = 10;
    private static final int MAXIMUM_POOL_SIZE_SIZE = 100;
    private static final int KEEP_ALIVE_TIME = 1;
    private static final int BLOCKING_QUEUE_CAPACITY = 100;
    private ExecutorService threadPool;
    private RpcRequestHandler rpcRequestHandler = new RpcRequestHandler();
    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);
    private final ServiceRegistry serviceRegistry;

    public RpcServer(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        this.threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE_SIZE, KEEP_ALIVE_TIME, TimeUnit.MINUTES, workQueue, threadFactory);
    }

    public void start(int port) {

        try (ServerSocket socketServer = new ServerSocket(port);) {
            logger.info("server starts");
            Socket socket;
            while ((socket = socketServer.accept()) != null) {
                logger.info("client connected");
                threadPool.execute(new RpcRequestHandlerRunnable(socket, rpcRequestHandler, serviceRegistry));
            }
        } catch (IOException e) {
            logger.error("occur IOException", e);
        }
    }

}