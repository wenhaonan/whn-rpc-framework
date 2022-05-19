package com.will;

import com.will.dto.RpcRequest;
import com.will.dto.RpcResponse;
import com.will.enums.RpcResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class ClientMessageHandlerThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientMessageHandlerThread.class);
    private Socket socket;
    private Object service;

    public ClientMessageHandlerThread(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Object result = invokeTargetMethod(rpcRequest);
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.error("occur exception", e);
        }
    }

    private Object invokeTargetMethod(RpcRequest rpcRequest) throws NoSuchMethodException, IllegalAccessException, ClassNotFoundException, InvocationTargetException {
        Class<?> clazz = Class.forName(rpcRequest.getInterfaceName());
        // 判断类是否实现了对应接口
        if (!clazz.isAssignableFrom(service.getClass())) {
            return RpcResponse.fail(RpcResponseCode.NOT_FOUND_CLASS);
        }
        Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
        if (null == method) {
            return RpcResponse.fail(RpcResponseCode.NOT_FOUND_METHOD);
        }
        return RpcResponse.success(method.invoke(service, rpcRequest.getParameters()));
    }
}
