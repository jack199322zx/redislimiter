package com.nino.redislimit.service;

import com.nino.redislimit.annotation.RedisLimit;
import org.springframework.stereotype.Service;

@Service
public class RedisLimitService {

    @RedisLimit(qpsLimit = 100, key = "test1")
    public void limit1(int i) {
        System.out.println("limit1:" + i);
    }

    @RedisLimit(qpsLimit = 50, key = "test2")
    public void limit2(int i) {
        System.out.println("limit2:" + i);
    }
}
