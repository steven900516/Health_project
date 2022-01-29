package com.lyx.health.annotation;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Steven0516
 * @create 2022-01-22
 */
@Slf4j
@Aspect
@Component
public class LimitAop {


    private static final Map<String, com.google.common.util.concurrent.RateLimiter> limitMap = new ConcurrentHashMap<>();


    @Pointcut("@annotation(com.lyx.health.annotation.Limiter)")
    public void rateLimit() {

    }

    @Around("rateLimit()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        //拿limit的注解
        Limiter limit = AnnotationUtils.findAnnotation(method,Limiter.class);
        if (limit != null) {
            //key作用：不同的接口，不同的流量控制

            RateLimiter rateLimiter = null;
            //验证缓存是否有命中key
            if (!limitMap.containsKey(method.getName())) {
                // 创建令牌桶
                rateLimiter = RateLimiter.create(limit.permitsPerSecond());
                limitMap.put(method.getName(), rateLimiter);
                log.info("新建了令牌桶={}，容量={}",method.getName(),limit.permitsPerSecond());
            }
            rateLimiter = limitMap.get(method.getName());
            // 拿令牌
            boolean acquire = rateLimiter.tryAcquire(limit.timeout(), limit.timeunit());
            // 拿不到命令，直接返回异常提示
            if (!acquire) {
                log.debug("令牌桶={}，获取令牌失败",method.getName());
                log.debug(limit.msg());
                throw new RuntimeException("请求频繁，请稍后再试~");
            }
        }
        return joinPoint.proceed();
    }



}
