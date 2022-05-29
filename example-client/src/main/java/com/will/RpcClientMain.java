package com.will;


import com.will.netty.RpcClientProxy;
import com.will.socket.SocketRpcClient;

public class RpcClientMain {
    public static void main(String[] args) {
        SocketRpcClient socketRpcClient = new SocketRpcClient("127.0.0.1", 9999);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(socketRpcClient);
        HelloService service = rpcClientProxy.getProxy(HelloService.class);
        // 调用未注册类
        HelloService1 service1 = rpcClientProxy.getProxy(HelloService1.class);
        String hello = service.hello(new Hello("111", "222"));
        String hello1 = service1.hello(new Hello("222", "333"));
        System.out.println(hello);
        System.out.println(hello1);
    }
}
