package com.will;

public class RpcClientMain {
    public static void main(String[] args) {
        RpcClientProxy rpcClientProxy = new RpcClientProxy("127.0.0.1", 9999);
        HelloService proxy = rpcClientProxy.getProxy(HelloService.class);
        String hello = proxy.hello(new Hello("111", "222"));
        System.out.println(hello);
    }
}
