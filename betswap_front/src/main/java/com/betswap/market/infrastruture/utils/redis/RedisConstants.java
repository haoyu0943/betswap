package com.betswap.market.infrastruture.utils.redis;

public class RedisConstants {
    public final static String REDIS_KEY_PREFIX_GOODS_ID = "REDIS_KEY_PREFIX_GOODS_ID_";

    public final static String SMS_CHECK = "sms_check_";
    public final static String SMS_CHECK_STATUS = "sms_check_status";
    public final static String MAIL_CHECK = "mail_check_";
    public final static String MAIL_CHECK_STATUS = "mail_check_status";
    public final static String CAPTCHA_CHECK = "captcha_check_";
    public final static String CAPTCHA_CHECK_STATUS = "captcha_check_status";

    public final static Long MAIL_EXPIRED_TIME = 300L;
    public final static Long SMS_EXPIRED_TIME = 300L;
    public final static Long CAPTCHA_EXPIRED_TIME = 300L;

    public final static Long MAIL_CHECK_EXPIRED_TIME = 300L;
    public final static Long SMS_CHECK_EXPIRED_TIME = 300L;
    public final static Long CAPTCHA_CHECK_EXPIRED_TIME = 300L;
}
