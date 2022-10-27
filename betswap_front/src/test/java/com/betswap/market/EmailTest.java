package com.betswap.market;

import com.betswap.market.app.user.impl.EmailService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(classes = BetswapApplication.class)
@RunWith(SpringRunner.class)
public class EmailTest {
    @Autowired
    private EmailService emailService;

    //@Test
    public void test(){
        emailService.sendEmail("2812389841@qq.com","123");
    }

}

