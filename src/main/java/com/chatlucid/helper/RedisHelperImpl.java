package com.chatlucid.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisHelperImpl implements RedisHelper {
    private final RedissonClient redissonClient;

    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        return clazz.cast(redissonClient.getBucket(key).get());
    }

    @Override
    public <T> T getAndSetObject(String key, T value) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        return bucket.getAndSet(value);
    }

    @Override
    public <T> T getAndSetObject(String key, T value, long ttlInSec) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        return bucket.getAndSet(value, ttlInSec, TimeUnit.SECONDS);
    }

}
