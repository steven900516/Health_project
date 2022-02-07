package com.lyx.health.AOP;

import cn.hutool.json.JSONString;
import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.RateLimiter;
import com.lyx.health.annotation.Limiter;
import com.lyx.health.annotation.LogInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.Json;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author Steven0516
 * @create 2022-02-04
 */

@Slf4j
@Aspect
@Component
public class LogAop {
    @Pointcut("@annotation(com.lyx.health.annotation.LogInfo)")
    public void Cut() {

    }



    @Before("Cut()")
    public void doBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogInfo annotation = method.getAnnotation(LogInfo.class);
        if (Objects.isNull(annotation)) {
            return;
        }
        String methodName = method.getDeclaringClass().getSimpleName() + "." + method.getName();
        log.info("方法用途 {}: start {}：入参：{}",annotation.methodName(), methodName, JSON.toJSONString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "Cut()", returning = "result")
    public void afterReturn(JoinPoint joinPoint, Object result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogInfo annotation = method.getAnnotation(LogInfo.class);
        if (Objects.isNull(annotation)) {
            return;
        }
        String methodName = method.getDeclaringClass().getSimpleName() + "." + method.getName();
        log.info("方法用途 {}： end {}：响应：{}",annotation.methodName(), methodName, JSON.toJSONString(result));
    }
}
