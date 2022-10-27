package com.betswap.market.infrastruture.utils.helper.mail;

import com.betswap.market.infrastruture.utils.helper.token.JwtPublicHelper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Random;

@ConfigurationProperties(prefix = "config.mail")
@Component
public class MailTokenHelper extends JwtPublicHelper {

    public String createNumber(int digit){
        String text = "1234567890";
        String result = "";
        Random random = new Random();
        for (int i = 0; i < digit; i++) {
            int index = random.nextInt(text.length());
            char c = text.charAt(index);
            result += c;
        }
        return result;
    }

    public String createString(int digit){
        String text = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String result = "";
        Random random = new Random();
        for (int i = 0; i < digit; i++) {
            int index = random.nextInt(text.length());
            char c = text.charAt(index);
            result += c;
        }
        return result;
    }




}
