package com.will.register;

/**
 * 服务注册中心接口
 *
 * @author haonan.wen
 * @createTime 2022/5/19 下午3:33
 */
public interface ServiceRegistry {

    <T> void register(T service);

    Object getService(String serviceName);
}
