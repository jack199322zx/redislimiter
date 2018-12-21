package com.nino.redislimit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class RedisLimitApplication {

    @Bean
    public ExecutorService executorService() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                30L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
    }


    public static void main(String[] args) {
        SpringApplication.run(RedisLimitApplication.class, args);
    }

}

