package com.nino.redislimit.aop;

import com.nino.redislimit.annotation.RedisLimit;
import com.nino.redislimit.redis.JedisClientPool;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class RedisAspect {

    private static final int limitInternal = 30 * 60 * 1000;

    @Pointcut("@annotation(com.nino.redislimit.annotation.RedisLimit)")
    private void pointCut() {

    }
    /**
     * 前置通知：目标方法执行之前执行以下方法体的内容
     * @param jp
     */
    @Before("pointCut()")
    public void beforeMethod(JoinPoint jp){
        String methodName = jp.getSignature().getName();
        System.out.println("前置通知:" + methodName + " begins with " + Arrays.asList(jp.getArgs()));
    }

    @Around("pointCut()")
    public void around(JoinPoint joinPoint){
        RedisLimit redisLimit = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(RedisLimit.class);
        String key = redisLimit.key();
        if (!JedisClientPool.exists(key)) {
            JedisClientPool.setEx(key, limitInternal, System.currentTimeMillis() + ":0");
            return;
        }
        String[] result = JedisClientPool.get(key).split(":");
        long start = System.currentTimeMillis();
        long internal = start - Long.parseLong(result[0]);
        System.out.println("internal is :" + internal);
        if (internal <= 1000) {
            Long currentCount = Long.parseLong(result[1]);
            if (currentCount <= redisLimit.qpsLimit()) {
                JedisClientPool.set(key, start + ":" + ++currentCount);
                return;
            }
            System.out.println("qps is over limit!!!");
        } else {
            JedisClientPool.set(key, start + ":1");
        }
        try {
            ((ProceedingJoinPoint) joinPoint).proceed();
        } catch (Throwable e) {
            throw new RuntimeException("target method proceed exception!");
        }
    }

}
