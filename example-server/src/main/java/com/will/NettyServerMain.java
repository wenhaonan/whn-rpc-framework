package com.will;


import com.will.netty.NettyRpcServer;

/**
 * @author haonan.wen
 * @createTime 2022/5/26 上午10:45
 */
public class NettyServerMain {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        NettyRpcServer nettyRpcServer = new NettyRpcServer("127.0.0.1", 8001);
        // 手动注册
        nettyRpcServer.publishService(helloService, HelloService.class);
        nettyRpcServer.run();
    }
}
