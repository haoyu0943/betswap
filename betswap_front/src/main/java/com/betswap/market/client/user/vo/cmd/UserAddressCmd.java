package com.betswap.market.client.user.vo.cmd;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//用户收货地址
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAddressCmd {

    private String  addressId;   //收货地址
//    private String  userId;
    @NotBlank(message = "verification_0039")
    @ApiParam(required = true)
    private String  userAddress;   //收货地址


    @NotBlank(message = "verification_0003")
    @ApiParam(required = true)
    private String  phone;         //收货人手机号

    @NotBlank(message = "verification_0040")
    @ApiParam(required = true)
    private String  name;          //收货人姓名

    @NotNull(message = "verification_0041")
    @ApiParam(required = true)
    private Integer addressType;    //地址类型，0 家，1 公司 2 学校，3 朋友，4 部门 ，5其他
    private Boolean defaultKey;     //是否是默认地址
}
