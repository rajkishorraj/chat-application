package com.chatlucid.helper;

public interface RedisHelper {
    <T> T getObject(String key, Class<T> clazz);
    <T> T getAndSetObject(String key, T value);
    <T> T getAndSetObject(String key, T value, long ttlInSecs);
}
