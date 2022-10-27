package com.betswap.market.infrastruture.utils.helper.token;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT的token，区分大小写
 */
@ConfigurationProperties(prefix = "config.token")
@Component
public class LoginTokenHelper extends JwtPublicHelper {

}
