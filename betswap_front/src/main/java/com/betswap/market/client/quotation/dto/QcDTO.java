package com.betswap.market.client.quotation.dto;

import lombok.Data;


//投注界面 全场显示 的界面
@Data
public class QcDTO {

    private QuotationDTO left; //左侧

    private QuotationDTO center;//中间

    private QuotationDTO right;//右侧
}
