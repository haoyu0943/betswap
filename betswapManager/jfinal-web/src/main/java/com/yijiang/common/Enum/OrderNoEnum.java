package com.yijiang.common.Enum;

public enum OrderNoEnum {
    SHOP    ("s",   0),
    SHOP_GROUP("s", 8),
    MOVIE   ("m",   1),
    FUND    ("f",   2);

    public final String typeName;
    public final int id;
    OrderNoEnum(String typeName, int id){
        this.typeName = typeName;
        this.id = id;
    }

    public static String getById(int id) {
        for (OrderNoEnum type : values()) {
            if (type.id==(id)) {
                return type.typeName;
            }
        }
        return null;
    }
}