package com.saiyan.zookeeper.autoconfigure;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * @author: saiyan
 * @date: 2020-04-06
 * @description: {@link EnableAutoConfiguration Auto-configuration} for Spring Boot Zookeeper Curator support.
 */
@Configuration
@ConditionalOnClass({ZooKeeper.class, CuratorFramework.class})
@EnableConfigurationProperties(ZookeeperCuratorProperties.class)
public class ZookeeperCuratorAutoConfiguration {

    private final ZookeeperCuratorProperties zookeeperCuratorProperties;

    public ZookeeperCuratorAutoConfiguration(ZookeeperCuratorProperties zookeeperCuratorProperties){
        this.zookeeperCuratorProperties=zookeeperCuratorProperties;
    }

    @Bean
    @ConditionalOnMissingBean(CuratorFramework.class)
    public CuratorFramework curatorFramework(RetryPolicy retryPolicy) {
        final CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
        Assert.hasText(zookeeperCuratorProperties.getConnectString(),"connectString must not be null");
        builder.connectString(zookeeperCuratorProperties.getConnectString());
        if(zookeeperCuratorProperties.getConnectionTimeoutMs()>0){
            builder.connectionTimeoutMs(zookeeperCuratorProperties.getConnectionTimeoutMs());
        }
        if(zookeeperCuratorProperties.getSessionTimeOutMs()>0){
            builder.sessionTimeoutMs(zookeeperCuratorProperties.getSessionTimeOutMs());
        }
        if(retryPolicy!=null){
            builder.retryPolicy(retryPolicy);
        }
        CuratorFramework client=builder.build();
        client.start();
        return client;
    }

    @Bean
    @ConditionalOnMissingBean(RetryPolicy.class)
    public RetryPolicy retryPolicy() {
        ZookeeperCuratorProperties.Retry retryProperties=zookeeperCuratorProperties.getRetry();
        return new ExponentialBackoffRetry(retryProperties.getBaseSleepTimeMs(), retryProperties.getMaxRetries());
    }
}
