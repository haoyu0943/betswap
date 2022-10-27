package com.betswap.market.client.user.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrentUserVO {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(value = "当前用户id", notes ="...", example = "1", hidden = true)
    String userId;

    @ApiModelProperty(value = "用户登录Token", required = true)
    String userToken;
}
