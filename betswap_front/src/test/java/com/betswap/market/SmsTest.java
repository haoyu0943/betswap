package com.betswap.market;

import com.betswap.market.infrastruture.utils.helper.sms.SMSHelper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = BetswapApplication.class)
@RunWith(SpringRunner.class)
public class SmsTest {
    @Autowired
    private SMSHelper smsHelper;

    //@Test
    public void test(){
        smsHelper.sendSMS("+8613121273120","1234");
    }
}
