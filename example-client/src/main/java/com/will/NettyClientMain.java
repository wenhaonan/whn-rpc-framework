package com.will;

import com.will.transport.RpcClient;
import com.will.transport.RpcClientProxy;
import com.will.transport.netty.NettyRpcClient;

/**
 * @author haonan.wen
 * @createTime 2022/5/26 上午10:49
 */
public class NettyClientMain {
    public static void main(String[] args) {
        RpcClient rpcClient = new NettyRpcClient("127.0.0.1", 9999);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcClient);
        HelloService service = rpcClientProxy.getProxy(HelloService.class);
        String hello = service.hello(new Hello("111", "222"));
        System.out.println(hello);
    }
}
