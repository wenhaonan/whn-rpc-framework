package com.will.register;

import java.net.InetSocketAddress;

/**
 * 服务注册中心
 *
 * @author haonan.wen
 * @createTime 2022/5/29 下午5:12
 */
public interface ServiceRegistry {
    /**
     * 注册服务
     *
     * @param serviceName       服务名称
     * @param inetSocketAddress 提供服务的地址
     */
    void registerService(String serviceName, InetSocketAddress inetSocketAddress);

    /**
     * 查找服务
     *
     * @param serviceName 服务名称
     * @return 提供服务的地址
     */
    InetSocketAddress lookupService(String serviceName);
}
