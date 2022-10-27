package com.yijiang.common.Enum;

public enum ResponseEnum {
    SUCCESS(1000,"sucess"),
    TOKEN_ERROR(1001,"token error"),
    USER_NOT_EXISTS(1002,"user not exists"),
    MOULD_ID_NOT_EXISTS(1003,"mould id not exists"),
    HAVE_NO_THIS_PRICE(1004,"There is no blind box at this price "),

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
