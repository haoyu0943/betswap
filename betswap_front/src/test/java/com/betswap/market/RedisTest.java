package com.betswap.market;

import com.betswap.market.infrastruture.utils.redis.RedisUtil;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(classes = BetswapApplication.class)
@RunWith(SpringRunner.class)
public class RedisTest {
    @Autowired
    private RedisUtil redisUtil;

    //@Test
    public void test(){
        redisUtil.set("123","321");
        System.out.println(redisUtil.get("123"));
    }
}
