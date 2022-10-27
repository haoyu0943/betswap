package com.betswap.market.adapter.user;

import java.util.HashMap;
import java.util.Map;

import com.betswap.market.infrastruture.common.response.ResponseEnum;
import com.betswap.market.infrastruture.common.response.ServerResponse;
import com.betswap.market.infrastruture.config.annotation.DisableToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/domain")
//解决跨域问题：指定允许跨域的域名
@CrossOrigin(origins = "http://test.do")
public class DomainControl {

    @Autowired
    private MessageSource messageSource;

    /**
     * 多语言测试
     */
    @DisableToken
    @PostMapping("/i18ntest")
    public Map<Object, Object> i18nTest() {
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("code", 5001);
        result.put("msg", messageSource.getMessage("error.error_5001", null, LocaleContextHolder.getLocale()));
        return result;
    }
    /**
     * 多语言测试
     * @return
     */
    @DisableToken
    @PostMapping("/i18ntest1")
    public ServerResponse i18nTest1() {
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.PASSWORD_WRONG);
    }


    @DisableToken
    @PostMapping("/i18ntest2")
    public ServerResponse i18ntest2() {
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.LOGIN_SUCCESS).message("我去asd");
    }


}
