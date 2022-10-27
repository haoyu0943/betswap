package com.betswap.market;

import com.betswap.market.infrastruture.utils.helper.idcard.IDCheckHelper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = BetswapApplication.class)
@RunWith(SpringRunner.class)
public class IDCheckTest
{
    @Autowired
    private IDCheckHelper idCheckHelper;
    //@Test
    public void test() {
        System.out.println(idCheckHelper.CheckPhoneAndName("13121273120","梁梓豪"));
    }
}
