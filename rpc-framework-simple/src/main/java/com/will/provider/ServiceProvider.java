package com.will.provider;

/**
 * 服务注册中心接口: 服务端调用
 *
 * @author haonan.wen
 * @createTime 2022/5/19 下午3:33
 */
public interface ServiceProvider {

    <T> void register(T service);

    Object getService(String serviceName);
}
