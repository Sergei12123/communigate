package com.example.diplom.service;

import com.example.diplom.manager.SessionService;
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
public class UserCache {

    private final RedisTemplate<String, String> redisTemplate;

    private final SessionService sessionService;

    @Cacheable(value = "usernameCache", key = "#username")
    public String get(String username) {
        return MyEncoder.decodeValue(redisTemplate.opsForValue().get(username));
    }

    @Cacheable(value = "currentUserCache", key = "#root.methodName")
    public String getSessionIdForCurrentUser() {
        final String currentUserName = sessionService.getCurrentUserName();

        return currentUserName == null ? null : MyEncoder.decodeValue(redisTemplate.opsForValue().get(currentUserName));
    }

    public void set(final String username, final String sessionId) {
        redisTemplate.opsForValue().set(username, MyEncoder.encodeValue(sessionId));
    }

    public Map<String, String> getAll() {
        Map<String, String> keyValues = new HashMap<>();
        Set<String> keys = redisTemplate.keys("*");
        for (String key : Objects.requireNonNull(keys)) {
            keyValues.put(key, get(key));
        }
        return keyValues;
    }

    public void delete(String username) {
        redisTemplate.delete(username);
    }
}
