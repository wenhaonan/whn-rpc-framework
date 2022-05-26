package com.will.transport.socket;

import com.will.dto.RpcRequest;
import com.will.dto.RpcResponse;
import com.will.enums.RpcErrorMessageEnum;
import com.will.enums.RpcResponseCode;
import com.will.exception.RpcException;
import com.will.transport.RpcClient;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@AllArgsConstructor
public class SocketRpcClient implements RpcClient {

    public static final Logger logger = LoggerFactory.getLogger(SocketRpcClient.class);

    private String host;
    private int port;

    @Override
    public Object sendRpcRequest(RpcRequest rpcRequest) {
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(rpcRequest);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            RpcResponse rpcResponse = (RpcResponse) objectInputStream.readObject();
            if (null == rpcResponse) {
                logger.error("调用服务失败，serviceName:{}", rpcRequest.getInterfaceName());
                throw new RpcException(RpcErrorMessageEnum.SERVICE_INVOCATION_FAILURE, "interfaceName" + rpcRequest.getInterfaceName());
            }
            if (null == rpcResponse.getCode() || !rpcResponse.getCode().equals(RpcResponseCode.SUCCESS.getCode())) {
                logger.error("调用服务失败，serviceName:{},response:{}", rpcRequest.getInterfaceName(), rpcResponse);
                throw new RpcException(RpcErrorMessageEnum.SERVICE_INVOCATION_FAILURE, "interfaceName" + rpcRequest.getInterfaceName());
            }
            return rpcResponse.getData();
        } catch (IOException | ClassNotFoundException e) {
            throw new RpcException("调用服务失败:", e);
        }
    }
}
