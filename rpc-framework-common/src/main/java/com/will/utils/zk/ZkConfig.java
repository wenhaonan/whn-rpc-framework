package com.will.utils.zk;

/**
 * @author haonan.wen
 * @createTime 2022/5/29 下午5:19
 */
public class ZkConfig {
    private int MAX_RETRIES = 3;
    private int SLEEP_MS_BETWEEN_RETRIES = 100;
    private String CONNECT_STRING = "127.0.0.1:2181";
    private int CONNECTION_TIMEOUT_MS = 10 * 1000;
    private int SESSION_TIMEOUT_MS = 60 * 1000;
    private String ZK_REGISTER_ROOT_PATH = "/my-rpc";

    public int getMAX_RETRIES() {
        return MAX_RETRIES;
    }

    public void setMAX_RETRIES(int MAX_RETRIES) {
        this.MAX_RETRIES = MAX_RETRIES;
    }

    public int getSLEEP_MS_BETWEEN_RETRIES() {
        return SLEEP_MS_BETWEEN_RETRIES;
    }

    public void setSLEEP_MS_BETWEEN_RETRIES(int SLEEP_MS_BETWEEN_RETRIES) {
        this.SLEEP_MS_BETWEEN_RETRIES = SLEEP_MS_BETWEEN_RETRIES;
    }

    public String getCONNECT_STRING() {
        return CONNECT_STRING;
    }

    public void setCONNECT_STRING(String CONNECT_STRING) {
        this.CONNECT_STRING = CONNECT_STRING;
    }

    public int getCONNECTION_TIMEOUT_MS() {
        return CONNECTION_TIMEOUT_MS;
    }

    public void setCONNECTION_TIMEOUT_MS(int CONNECTION_TIMEOUT_MS) {
        this.CONNECTION_TIMEOUT_MS = CONNECTION_TIMEOUT_MS;
    }

    public int getSESSION_TIMEOUT_MS() {
        return SESSION_TIMEOUT_MS;
    }

    public void setSESSION_TIMEOUT_MS(int SESSION_TIMEOUT_MS) {
        this.SESSION_TIMEOUT_MS = SESSION_TIMEOUT_MS;
    }

    public String getZK_REGISTER_ROOT_PATH() {
        return ZK_REGISTER_ROOT_PATH;
    }

    public void setZK_REGISTER_ROOT_PATH(String ZK_REGISTER_ROOT_PATH) {
        this.ZK_REGISTER_ROOT_PATH = ZK_REGISTER_ROOT_PATH;
    }
}
