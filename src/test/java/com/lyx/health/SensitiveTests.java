package com.lyx.health;

import com.lyx.health.util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Steven0516
 * @create 2022-01-23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = HealthApplication.class)
public class SensitiveTests {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter() {
        //这是发的人比较初级的
        String text = "I'm a piece of shit，我就是傻逼呀，我个笨蛋，" + "留下不学无术的眼泪!!!!";
        text = sensitiveFilter.filter(text);
        System.out.println(text);//I'm a piece of ***，我就是***呀，我个***，留下不学无术的眼泪!!!!

        text = "I'm a piece of ☆sh☆it，爆乳我就是☆傻☆☆逼☆呀，@我个☆笨☆蛋，" +  "留下不学无术的眼泪!!!";
        text = sensitiveFilter.filter(text);
        System.out.println(text);//I'm a piece of ☆***，我就是☆***☆呀，@我个☆***，留下不学无术的眼泪!!!
    }
}
