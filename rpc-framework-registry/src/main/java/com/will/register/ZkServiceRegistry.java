package com.will.register;

import com.will.zk.CuratorHelper;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * 基于zookeeper的服务注册中心
 *
 * @author haonan.wen
 * @createTime 2022/5/29 下午5:13
 */
public class ZkServiceRegistry implements ServiceRegistry {
    private static final Logger logger = LoggerFactory.getLogger(ZkServiceRegistry.class);
    private final CuratorFramework zkClient;

    public ZkServiceRegistry() {
        this.zkClient = CuratorHelper.getZkClient();
        this.zkClient.start();
    }


    @Override
    public void registerService(String serviceName, InetSocketAddress inetSocketAddress) {
        // 跟节点下注册接口名
        StringBuilder servicePath = new StringBuilder(CuratorHelper.getZkConfig().getZK_REGISTER_ROOT_PATH()).append("/").append(serviceName);
        // 接口下注册服务地址
        servicePath.append(inetSocketAddress.toString());
        CuratorHelper.createEphemeralNode(zkClient, servicePath.toString());
        logger.info("接口注册成功，节点为：{}", servicePath);
    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        String serviceAddress = CuratorHelper.getChildrenNodes(zkClient, serviceName).get(0);
        logger.info("成功找到服务地址:{}", serviceAddress);
        return new InetSocketAddress(serviceAddress.split(":")[0], Integer.parseInt(serviceAddress.split(":")[1]));

    }
}
