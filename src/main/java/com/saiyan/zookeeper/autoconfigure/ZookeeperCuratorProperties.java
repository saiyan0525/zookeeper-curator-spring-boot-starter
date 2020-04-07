package com.saiyan.zookeeper.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: saiyan
 * @date: 2020-04-06
 * @description: Configuration properties for Zookeeper Curator.
 */
@ConfigurationProperties("zookeeper.curator")
public class ZookeeperCuratorProperties {
    /**
     * list of servers to connect to
     */
    private String connectString="localhost:2181";
    /**
     * session timeout
     */
    private int sessionTimeOutMs;
    /**
     * connection timeout
     */
    private int connectionTimeoutMs;
    /**
     * new client retry properties
     */
    private final Retry retry=new Retry();
    /**
     * Retry properties.
     */
    public static class Retry {
        /**
         * initial amount of time to wait between retries
         */
        private int baseSleepTimeMs=1000;
        /**
         * max number of times to retry
         */
        private int maxRetries=3;

        public int getBaseSleepTimeMs() {
            return baseSleepTimeMs;
        }

        public void setBaseSleepTimeMs(int baseSleepTimeMs) {
            this.baseSleepTimeMs = baseSleepTimeMs;
        }

        public int getMaxRetries() {
            return maxRetries;
        }

        public void setMaxRetries(int maxRetries) {
            this.maxRetries = maxRetries;
        }
    }

    public String getConnectString() {
        return connectString;
    }

    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }

    public int getSessionTimeOutMs() {
        return sessionTimeOutMs;
    }

    public void setSessionTimeOutMs(int sessionTimeOutMs) {
        this.sessionTimeOutMs = sessionTimeOutMs;
    }

    public int getConnectionTimeoutMs() {
        return connectionTimeoutMs;
    }

    public void setConnectionTimeoutMs(int connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }

    public Retry getRetry() {
        return retry;
    }
}
