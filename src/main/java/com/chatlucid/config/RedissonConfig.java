package com.chatlucid.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RedissonConfig {

    @Value("${redis.server.host}")
    private String node;

    @Value("${redis.server.password}")
    private String password;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        Config config = new Config();
        log.debug("node: {}, pass: {}", node, password);
        config.useSingleServer()
                .setAddress(node)
                .setPassword(password);
        return Redisson.create(config);
    }
}
