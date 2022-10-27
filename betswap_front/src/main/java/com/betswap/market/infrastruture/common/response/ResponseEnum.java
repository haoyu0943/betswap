package com.betswap.market.infrastruture.common.response;

public enum ResponseEnum {
    SUCCESS(1000,"message_1000"),
    FAILED(1001,"message_1001"),

    INTERNAL_ERROR(2001, "message_2001"),
    LOGIN_SUCCESS(2002, "message_2002"),
    LOGIN_FAILED(2003, "message_2003"),
    PASSWORD_WRONG(2004, "message_2004"),
    ACCOUNT_NOT_FOUND(2005, "message_2005"),
    ACCOUNT_NOT_LOGIN(2006, "message_2006"),
    ACCOUNT_TYPE_WRONG(2007, "message_2007"),
    ACCOUNT_PRIVILEGE_WRONG(2008, "message_2008"),
    PASSWORD_NULL(2009, "message_2009"),
    MONNEYPASSWORD_NULL(2009, "message_2010"),

    //注册类错误
    REGISTER_SUCCESS(3000, "message_3000"),
    REGISTER_FAILED(3001, "message_3001"),
    INVALID_PARAM(3002, "message_3002"),
    USERNAME_EXIST(3003, "message_3003"),
    UPDATE_PASSWORD_FAILED(3004, "message_3004"),
    MISSING_PARAM(3005, "message_3005"),
    UNMATCHED_PARAM(3006, "message_3006"),
    EMAIL_EXIST(3007, "message_3007"),
    EMAIL_NOT_EXIST(3008, "message_3008"),
    PHONE_EXIST(3010, "message_3010"),
    PHONE_NOT_EXIST(3011, "message_3011"),
    PHONE_ERROR(3012, "message_3012"),
    ALREADY_ID_CHECK(3013,"message_3013"),
    NOT_YET_ID_CHECK(3014,"message_3014"),
    ID_CHECK_FAILED(3015,"message_3015"),
    PHONE_NOT_BIND(3016,"message_3016"),
    EMAIL_NOT_BIND(3017,"message_3017"),
    WECHAT_NOT_BIND(3018,"message_3018"),
    IDCARD_EXIST(3019, "message_3019"),

    TOKEN_ERROR(4002, "message_4002"),
    TOKEN_EXPIRED(4001, "message_4001"),
    CAPTCHA_EXPIRED(4003, "message_4003"),
    CAPTCHA_ERROR(4004, "message_4004"),
    SMS_EXPIRED(4005, "message_4005"),
    SMS_ERROR(4006, "message_4006"),
    MAIL_EXPIRED(4007, "message_4007"),
    MAIL_ERROR(4008, "message_4008"),
    SIGN_ERROR(4009, "message_4009"),
    TOKEN_INVALID(4010, "message_4010"),
    SMS_SUBMIT(4011, "message_4011"),
    MAIL_SUBMIT(4012, "message_4012"),

    //地址类错误
    ADDRESS_EXIST(5001, "message_5001"),
    ADDRESS_NOT_EXIST(5002, "message_5002"),
    ADDRESS_NOT_MATCH_USER(5003, "message_5003"),

    //商品类错误
    GOODS_STOCK_EMPTY(6001, "message_6001"),
    GOODS_NOT_EXIST(6002, "message_6002"),
    GOODS_NOT_IN_KEEPING(6003, "message_6003"),
    GOODS_OWNER_WRONG(6004, "message_6004"),
    LABELS_OVER_LIMIT(6005, "message_6005"),
    GOODS_SELLING_TIMEOUT(6006,"message_6006"), //不在销售时段内 （比如电影票有购买时间）
    EXCEED_PER_LIMIT(6007,"message_6007"),      //超出个人购买上限
    SHOP_VOL_NOT_ENOUGH(6008,"message_6008"),   //商店消费量不满足要求


    //文件类错误
    FILE_TYPE_INVALID(7001,"message_7001"),

    //订单错误
    BALANCE_NOT_ENOUGH(8001,"message_8001"),//余额不足
    PAY_FAILED      (8002,"message_8002"),  //申请支付失败
    ORDER_TIMEOUT   (8003,"message_8003"),  //订单已经过期
    ABNORMAL_AMOUNT (8004,"message_8004"),  //金额异常
    CANNT_FINDORDER (8005,"message_8005"),  //无法找到订单
    CANNT_CANCELORDER (8006,"message_8006"),  //订单已经结算，无法取消

    //数据类错误
    GET_SUCCESS(20000, "message_20000"),
    GET_FAILED(20001, "message_20001"),
    SAVE_SUCCESS(20002, "message_20002"),
    SAVE_FAILED(20003, "message_20003"),
    DATA_NOT_EXIST(20004, "message_20004"),
    DATA_NOT_MATCH(20005,"message_20005"),
    DATA_NON_EXECUTABLE(20006,"message_20006"),

    //链上错误
    SAVE_IN_BLOCKCHAIN_FAILED(30001,"message_30001"),
    SAVE_FILE_FAILED(30002,"message_30002"),
    FILE_ALREADY_EXIST(30003,"message_30003"),
    SIGN_DATA_VERIFY_FAILED(30004,"message_30004"),
    QTY_OUTOF_MAXNUMB(30005,"message_30005"),
    TRANSFER_FAIL(30006,"message_30006"),

    //自定义
    NO_SUCH_DATA(40001,"message_40001"),//无此数据
    SAVE_FAIL(40002,"message_40002"),//保存失败
    ABNORMAL_STATUS(40003,"message_40003"),//状态异常
    UUID_TIMEOUT(40004,"message_40004"),//本链接的uuid可能已经过期，请重新发送邮件并在一小时内修改
    FOLLOW_SUCCESS(40005,"message_40005"),//关注成功
    UNFOLLOW_SUCCESS(40006,"message_40006"),//取消关注成功
    SHOP_EXIST(40007,"message_40007"),//已存在商铺
    NULL_POINTER_EXCEPTION(40008,"message_40008"),//空指针异常
    ALREADY_EXISTS_DATA(40009,"message_40009"),//数据已存在
    INVITATION_CODE_INVALID(40010,"message_40010"),//邀请码无效
    WALLET_NOT_SUCH(40011,"message_40011"),//无钱包信息
    COUPON_INVALID(40012,"message_40012"),//优惠券无效
    COUPON_LIMIT(40013,"message_40013"),//优惠券超过限制
    RONGCLOUDY_ERROR(40014,"message_40014"),//连接融云系统出现异常
    HAVING_IN_GROUP(40015,"message_40015"),//已参加该拼团
    TIME_OUT(40016,"message_40016"),//已经到达截止时间，不可操作
    CANNOT_CANCEL_BET(40017,"message_40017"),//已投注 不可取消
    CANNOT_CANCEL_STATUS(40018,"message_40018"),//非未开赛 不可取消
    SHORT_MARGIN(40019,"message_40019"),//保证金不足
    ;



    public final  Integer code;		//响应码
    public final  String message;		//响应信息

    ResponseEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Boolean checkCode(Integer code) {
        return this.code.equals(code);
    }
}