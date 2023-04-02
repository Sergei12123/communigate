package com.example.diplom.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Repository
@AllArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    @Cacheable(value = "myCache", key = "username")
    public String get(String username) {
        return MyEncoder.decodeValue(redisTemplate.opsForValue().get(username));
    }

    public void set(final String username, final String key) {
        redisTemplate.opsForValue().set(username, MyEncoder.encodeValue(key));
    }

    public Map<String, String> getAll() {
        Map<String, String> keyValues = new HashMap<>();
        Set<String> keys = redisTemplate.keys("*");
        for (String key : Objects.requireNonNull(keys)) {
            keyValues.put(key, redisTemplate.opsForValue().get(key));
        }
        return keyValues;
    }

    public void delete(String username) {
        redisTemplate.delete(username);
    }
}
