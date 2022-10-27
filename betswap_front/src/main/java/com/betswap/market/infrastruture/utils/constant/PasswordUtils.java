package com.betswap.market.infrastruture.utils.constant;


import java.util.Date;
import java.util.Random;

/**
 * @author aaronmegs
 * @create 2021/3/18 9:50 上午
 */
public class PasswordUtils {

    /**
     * 大小写字母
     */
    public final static String[] word = {
            "a", "b", "c", "d", "e", "f", "g",
            "h", "j", "k", "m", "n",
            "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G",
            "H", "J", "K", "M", "N",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
    };

    /**
     * 数字 1-9
     */
    public final static String[] num = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9"
    };



    public final static String[] type = {
            "word", "num"
    };

    /**
     * 随机生成 8-12 位包含 字母、数字 的密码
     *
     * @return
     */
    public static String randomPassword() {
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random(new Date().getTime());
        boolean flag = false;
        //输出几位密码长度  这里是有可能8 ，9 ，10 位
        int length = random.nextInt(3) + 8;
        for (int i = 0; i < length; i++) {
            if (flag) {
                stringBuffer.append(num[random.nextInt(num.length)]);
            } else {
                stringBuffer.append(word[random.nextInt(word.length)]);
            }
            flag = !flag;
        }
        return stringBuffer.toString();
    }

    /**
     * 随机生成 n 位包含 字母、数字、特殊字符 的密码
     *
     * @return
     */
    public static String randomPW(Integer count) {
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random(new Date().getTime());
        String flag = type[random.nextInt(type.length)];
        // 输出长度 12 位
        int length = count;
        for (int i = 0; i < length; i++) {
            switch (flag) {
                case "word":
                    stringBuffer.append(word[random.nextInt(word.length)]);
                    break;
                case "num":
                    stringBuffer.append(num[random.nextInt(num.length)]);
                    break;
                default:
                    break;
            }
            flag= type[random.nextInt(type.length)];
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(randomPW(6));
    }
}

