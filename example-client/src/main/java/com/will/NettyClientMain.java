package com.will;


import com.will.netty.NettyClientTransport;
import com.will.netty.RpcClientProxy;

/**
 * @author haonan.wen
 * @createTime 2022/5/26 上午10:49
 */
public class NettyClientMain {

    public static void main(String[] args) {
        RpcClientTransport rpcClient = new NettyClientTransport();
        RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcClient);
        HelloService service = rpcClientProxy.getProxy(HelloService.class);
        String hello = service.hello(new Hello("111", "222"));
        System.out.println(hello);
    }
}
