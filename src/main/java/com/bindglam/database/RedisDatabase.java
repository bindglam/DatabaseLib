package com.bindglam.database;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

public final class RedisDatabase implements Database<Jedis, JedisException> {
    private final String host;
    private final int port;
    private final int timeout;
    private final String password;
    private final int maxPoolSize;

    private JedisPool pool;

    public RedisDatabase(String host, int port, int timeout, String password, int maxPoolSize) {
        this.host = host;
        this.port = port;
        this.timeout = timeout;
        this.password = password;
        this.maxPoolSize = maxPoolSize;
    }

    @Override
    public void start() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(maxPoolSize);
        poolConfig.setMaxIdle(maxPoolSize);

        this.pool = new JedisPool(poolConfig, host, port, timeout, password);
    }

    @Override
    public void stop() {
        this.pool.close();
    }

    @Override
    public void getResource(ResourceConsumer<Jedis, JedisException> consumer) {
        try(Jedis resource = pool.getResource()) {
            consumer.accept(resource);
        } catch (JedisException e) {
            throw new RuntimeException("Failed to proceed redis database connection", e);
        }
    }
}
