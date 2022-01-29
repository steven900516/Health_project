package com.lyx.health;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Steven0516
 * @create 2022-01-26
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = HealthApplication.class)
public class RateLimiterTest {
    @Test
    public void rateLimiter() throws InterruptedException {
        //RateLimiter limiter = RateLimiter.create(10,2, TimeUnit.SECONDS);//QPS 100
        RateLimiter limiter = RateLimiter.create(10);
        long start = System.currentTimeMillis();
        for (int i= 0; i < 30; i++) {
//            double time = limiter.acquire();
            long after = System.currentTimeMillis() - start;
            boolean bo = limiter.tryAcquire();

            if (!bo) {
                System.out.println(i + ",limited,等待:" + bo + "，已开始" + after + "毫秒");
            } else {
                System.out.println(i + ",enough" + "，已开始" + after + "毫秒" );
            }

//            if (time > 0D) {
//                System.out.println(i + ",limited,等待:" + time + "，已开始" + after + "毫秒");
//            } else {
//                System.out.println(i + ",enough" + "，已开始" + after + "毫秒" );
//            }
            //模拟冷却时间，下一次loop可以认为是bursty开始
            if (i == 9) {
                Thread.sleep(2000);
            }
        }
        System.out.println("total time：" + (System.currentTimeMillis() - start));
    }
}
