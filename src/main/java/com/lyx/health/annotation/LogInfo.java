package com.lyx.health.annotation;

import java.lang.annotation.*;

/**
 * @author Steven0516
 * @create 2022-02-04
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogInfo {
    String methodName() default "";
}
