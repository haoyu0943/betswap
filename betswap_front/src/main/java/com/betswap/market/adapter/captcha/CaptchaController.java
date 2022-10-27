package com.betswap.market.adapter.captcha;

import com.betswap.market.infrastruture.common.response.ServerResponse;
import com.betswap.market.infrastruture.config.annotation.DisableToken;
import com.betswap.market.infrastruture.utils.redis.RedisUtil;
import com.betswap.market.infrastruture.common.response.ResponseEnum;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.UUID;

import static com.betswap.market.infrastruture.utils.redis.RedisConstants.*;

@RestController
@RequestMapping("/captcha")
@Api(value = "CaptchaController", tags = { "/captcha 验证码API" })
public class CaptchaController {

    @Autowired
    DefaultKaptcha defaultKaptcha;

    @Autowired
    private RedisUtil redisUtil;

    @DisableToken
    @PostMapping(value = "/getCaptcha")
    @ResponseBody
    public void getCaptcha(HttpServletResponse httpServletResponse)
            throws Exception {

        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // 生产验证码字符串并将生成的Token保存到返回头
            String createText = defaultKaptcha.createText();
            String captcha_token = UUID.randomUUID().toString();
            redisUtil.set(CAPTCHA_CHECK + captcha_token,createText,CAPTCHA_EXPIRED_TIME);
            redisUtil.set(CAPTCHA_CHECK_STATUS + captcha_token,false,CAPTCHA_CHECK_EXPIRED_TIME);

            httpServletResponse.setHeader("captcha_token", captcha_token);

            // 使用生成的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "captcha_token");
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    /**
     * Token校验验证码
     */
    @DisableToken
    @PostMapping(value = "/verifyCaptcha")
    @ResponseBody
    public ServerResponse verifyCaptcha(@RequestParam String captchaToken, @RequestParam String captchaContent) {
        String answer = redisUtil.getAsString(CAPTCHA_CHECK + captchaToken);

        if(answer == null){
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.CAPTCHA_EXPIRED);
        }
        else if(!answer.equals(captchaContent)){
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.CAPTCHA_EXPIRED);
        }
        redisUtil.set(CAPTCHA_CHECK_STATUS + captchaToken,true,CAPTCHA_CHECK_EXPIRED_TIME);
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS);

    }

}
