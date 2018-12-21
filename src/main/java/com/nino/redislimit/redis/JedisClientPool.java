package com.nino.redislimit.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

public final class JedisClientPool {
	
	private static JedisPool jedisPool;

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	static {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(8);
		config.setMaxActive(8);
		config.setMaxWait(2000);
		config.setMinIdle(0);
		config.setTestOnBorrow(false);
		config.setTestWhileIdle(false);
		jedisPool = new JedisPool(config, "127.0.0.1");
	}

	public static String set(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.set(key, value);
		jedisPool.returnResource(jedis);
		return result;
	}

	public static String setEx(String key, int expireTime, String value) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.setex(key, expireTime, value);
		jedisPool.returnResource(jedis);
		return result;
	}

	public static Boolean setNx(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.setnx(key, value);
		jedisPool.returnResource(jedis);
		return result == 1;
	}

	public static String get(String key) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.get(key);
		jedisPool.returnResource(jedis);
		return result;
	}

	public static Boolean exists(String key) {
		Jedis jedis = jedisPool.getResource();
		Boolean result = jedis.exists(key);
		jedisPool.returnResource(jedis);
		return result;
	}

	public static Long expire(String key, int seconds) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.expire(key, seconds);
		jedisPool.returnResource(jedis);
		return result;
	}

	public static Long ttl(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.ttl(key);
		jedisPool.returnResource(jedis);
		return result;
	}

	public static Long incr(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.incr(key);
		jedisPool.returnResource(jedis);
		return result;
	}

	public static Long hset(String key, String field, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hset(key, field, value);
		jedisPool.returnResource(jedis);
		return result;
	}

	public static String hget(String key, String field) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.hget(key, field);
		jedisPool.returnResource(jedis);
		return result;
	}

	public static Long hdel(String key, String... field) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hdel(key, field);
		jedisPool.returnResource(jedis);
		return result;
	}

	public static Boolean hexists(String key, String field) {
		Jedis jedis = jedisPool.getResource();
		Boolean result = jedis.hexists(key, field);
		jedisPool.returnResource(jedis);
		return result;
	}

	public static List<String> hvals(String key) {
		Jedis jedis = jedisPool.getResource();
		List<String> result = jedis.hvals(key);
		jedisPool.returnResource(jedis);
		return result;
	}

	public static Long del(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.del(key);
		jedisPool.returnResource(jedis);
		return result;
	}

}
