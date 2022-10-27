package com.betswap.market;


import org.junit.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CommonTest {

    @Test
    public void Test(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(timestamp));
    }

    @Test
    public void test2(){
        String res = ".pdf";
        System.out.println(res.substring(1));
    }
}
