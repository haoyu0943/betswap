package com.betswap.market.client.communicate.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SysMsgDTO {
    long    id;
    int     read;
    String  title;
    String  content;
    String  picUrl;
    Long   createTime;
}
