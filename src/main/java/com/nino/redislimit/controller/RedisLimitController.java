package com.nino.redislimit.controller;

import com.nino.redislimit.service.RedisLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;


@RestController
@RequestMapping("/test")
public class RedisLimitController {

    @Autowired
    RedisLimitService redisLimitService;
    @Autowired
    ExecutorService executorService;

    @RequestMapping("/limit1")
    public void test1() {
        IntStream.range(0, 1000).forEach(i -> {
            redisLimitService.limit1(i);
        });
    }

    @RequestMapping("/limit2")
    public void test2() {
        IntStream.range(0, 1000).forEach(i -> {
            redisLimitService.limit2(i);
        });
    }
}
