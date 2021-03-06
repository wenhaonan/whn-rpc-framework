package com.will.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务注册中心接口: 服务端调用
 *
 * @author haonan.wen
 * @createTime 2022/5/19 下午3:33
 */
public class CuratorHelper {
    private static final Logger logger = LoggerFactory.getLogger(CuratorHelper.class);
    private static final ZkConfig ZK_CONFIG = new ZkConfig();
    private static final Map<String, List<String>> serviceAddressMap = new ConcurrentHashMap<>();

    public static CuratorFramework getZkClient() {
        // 重试策略，重试3次，并在两次重试之间等待100毫秒，以防出现连接问题。
        RetryPolicy retryPolicy = new RetryNTimes(
                ZK_CONFIG.getMAX_RETRIES(), ZK_CONFIG.getSLEEP_MS_BETWEEN_RETRIES());
        return CuratorFrameworkFactory.builder()
                //要连接的服务器(可以是服务器列表)
                .connectString(ZK_CONFIG.getCONNECT_STRING())
                .retryPolicy(retryPolicy)
                //连接超时时间，10秒
                .connectionTimeoutMs(ZK_CONFIG.getCONNECTION_TIMEOUT_MS())
                //会话超时时间，60秒
                .sessionTimeoutMs(ZK_CONFIG.getSESSION_TIMEOUT_MS())
                .build();
    }

    /**
     * 创建临时节点
     * 临时节点驻存在ZooKeeper中，当连接和session断掉时被删除。
     */
    public static void createEphemeralNode(final CuratorFramework zkClient, final String path) {
        try {
            zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
        } catch (Exception e) {
            logger.error("occur exception:", e);
        }
    }

    /**
     * 获取某个字节下的子节点
     */
    public static List<String> getChildrenNodes(final CuratorFramework zkClient, final String serviceName) {
        if (serviceAddressMap.containsKey(serviceName)) {
            return serviceAddressMap.get(serviceName);
        }
        List<String> result = Collections.emptyList();
        String servicePath = ZK_CONFIG.getZK_REGISTER_ROOT_PATH() + "/" + serviceName;
        try {
            result = zkClient.getChildren().forPath(servicePath);
            serviceAddressMap.put(serviceName, result);
            registerWatcher(zkClient, serviceName);
        } catch (Exception e) {
            logger.error("occur exception:", e);
        }
        return result;
    }

    /**
     * 注册监听
     *
     * @param serviceName 服务名称
     */
    private static void registerWatcher(CuratorFramework zkClient, String serviceName) {
        String servicePath = ZK_CONFIG.getZK_REGISTER_ROOT_PATH() + "/" + serviceName;
        PathChildrenCache pathChildrenCache = new PathChildrenCache(zkClient, servicePath, true);
        PathChildrenCacheListener pathChildrenCacheListener = (curatorFramework, pathChildrenCacheEvent) -> {
            List<String> serviceAddresses = curatorFramework.getChildren().forPath(servicePath);
            serviceAddressMap.put(serviceName, serviceAddresses);
        };
        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
        try {
            pathChildrenCache.start();
        } catch (Exception e) {
            logger.error("occur exception:", e);
        }
    }

    public static ZkConfig getZkConfig() {
        return ZK_CONFIG;
    }

}
