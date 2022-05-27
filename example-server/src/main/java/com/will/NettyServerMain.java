package com.will;

import com.will.register.DefaultServiceRegistry;
import com.will.transport.netty.server.NettyRpcServer;

/**
 * @author haonan.wen
 * @createTime 2022/5/26 上午10:45
 */
public class NettyServerMain {

    public static void main(String[] args) {
        HelloServiceImpl helloService = new HelloServiceImpl();
        DefaultServiceRegistry defaultServiceRegistry = new DefaultServiceRegistry();
        // 手动注册
        defaultServiceRegistry.register(helloService);
        NettyRpcServer socketRpcServer = new NettyRpcServer(9999);
        socketRpcServer.run();
    }
}
