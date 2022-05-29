package com.will.provider;

import com.will.enums.RpcErrorMessageEnum;
import com.will.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的注册中心，使用map存储服务信息，服务端调用
 *
 * @author haonan.wen
 * @createTime 2022/5/19 下午3:34
 */
public class DefaultServiceProvider implements ServiceProvider {
    private static final Logger logger = LoggerFactory.getLogger(DefaultServiceProvider.class);

    /**
     * 接口名和服务的对应关系，TODO 处理一个接口被两个实现类实现的情况
     * key:service/interface name
     * value:service
     */
    private static final Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    private static final Set<String> registeredService = ConcurrentHashMap.newKeySet();

    /**
     * TODO 修改为注解注册
     * 将所有实现的接口都注册进去
     *
     * @param service
     * @param <T>
     */
    @Override
    public <T> void register(T service) {
        /**
         * getCanonicalName: com.will.HelloServiceImpl
         * getName:
         */
        String serviceName = service.getClass().getCanonicalName();
        if (registeredService.contains(serviceName)) {
            return;
        }
        registeredService.add(serviceName);
        Class[] interfaces = service.getClass().getInterfaces();
        if (interfaces.length == 0) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }
        for (Class anInterface : interfaces) {
            serviceMap.put(anInterface.getCanonicalName(), service);
        }
        logger.info("Add service: {} and interfaces:{}", serviceName, service.getClass().getInterfaces());
    }

    @Override
    public Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if (null == service) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND);
        }
        return service;
    }
}
