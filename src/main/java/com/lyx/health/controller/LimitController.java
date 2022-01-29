package com.lyx.health.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.lyx.health.annotation.Limiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @author Steven0516
 * @create 2022-01-22
 */
@Slf4j
@RestController
@RequestMapping("/limit")
public class LimitController {
    /**
     * 限流策略 ： 1秒钟2个请求
     */


    @GetMapping("/test1")
    @Limiter(permitsPerSecond = 1.0, timeout = 500, timeunit = TimeUnit.MILLISECONDS,msg = "当前排队人数较多，请稍后再试！")
    public String testLimiter() {
        //500毫秒内，没拿到令牌，就直接进入服务降级
        log.info("令牌桶limit获取令牌成功");
        return "ok";
    }


    private final RateLimiter limiter = RateLimiter.create(1.0);

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/test2")
    public String testLimiter2() {
        //500毫秒内，没拿到令牌，就直接进入服务降级
        boolean tryAcquire = limiter.tryAcquire(500, TimeUnit.MILLISECONDS);

        if (!tryAcquire) {
            log.warn("进入服务降级，时间{}", LocalDateTime.now().format(dtf));
            return "当前排队人数较多，请稍后再试！";
        }

        log.info("获取令牌成功，时间{}", LocalDateTime.now().format(dtf));
        return "请求成功";
    }


}