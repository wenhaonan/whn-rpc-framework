package com.will.netty;

import com.will.RpcClientTransport;
import com.will.dto.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

public class RpcClientProxy implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(RpcClientProxy.class);

    private RpcClientTransport rpcClientTransport;

    public RpcClientProxy(RpcClientTransport rpcClientTransport) {
        this.rpcClientTransport = rpcClientTransport;
    }

    public <T> T getProxy(Class<T> clazz) {
        /**
         * jdk proxy 只能代理接口，否则报错：java.lang.IllegalArgumentException: com.will.RpcClientMain is not an interface
         */
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("Call invoke method and invoked method: {}", method.getName());
        RpcRequest rpcRequest = RpcRequest.builder().methodName(method.getName())
                .parameters(args)
                .interfaceName(method.getDeclaringClass().getName())
                .paramTypes(method.getParameterTypes())
                .requestId(UUID.randomUUID().toString())
                .build();
        return rpcClientTransport.sendRpcRequest(rpcRequest);
    }
}
