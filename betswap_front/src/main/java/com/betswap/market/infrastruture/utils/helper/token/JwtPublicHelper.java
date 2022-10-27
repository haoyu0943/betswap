package com.betswap.market.infrastruture.utils.helper.token;

import com.betswap.market.infrastruture.utils.helper.encrypt.DESEncryptHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
public class JwtPublicHelper {
    private static final String CLAIM_KEY_CONTENT = "content";
    private static final String CLAIM_KEY_CREATED = "created";

    protected String secret;
    protected long expire;
    protected String header;

    public static int TOKEN_SUCCESS = 0;
    public static int TOKEN_NULL    = 1;
    public static int TOKEN_EXPIRED = 2;
    public static int SIGN_FAULT    = 3;
    public static int TOKEN_WRONG   = 4;

    /**
     * 生成token
     */
    public String createToken (String subject){
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);//过期时间

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(DESEncryptHelper.DESEncrpyt(subject,secret))
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     *  Token 验证
     *  使用DES加密算法
     **/
    public int checkToken(String token,String code){

        //token为空
        if(token == null || "".equals(token)){ return(TOKEN_NULL); }

        Claims claims = null;
        try{
            claims = getTokenClaim(token);
            //签名错误
            if(claims == null){ return (SIGN_FAULT); }
            //过期
            if(isTokenExpired(claims.getExpiration())){ return(TOKEN_EXPIRED); }

        }catch (Exception e){
            return(TOKEN_WRONG);
        }
        //检验答案
        if(DESEncryptHelper.DESDecrpyt(claims.getSubject(),secret).equals(code))
            return(TOKEN_SUCCESS);
        else
            return (TOKEN_WRONG);
    }

    /**
     * 获取token subject
     */
    public String getTokenSubject (String token) {
        try {
            return DESEncryptHelper.DESDecrpyt(getTokenClaim(token).getSubject(),secret);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 获取token Claim
     */
    public Claims getTokenClaim (String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch (Exception e){
            return null;
        }
    }


    /**
     * 验证token是否过期失效
     */
    public boolean isTokenExpired (Date expirationTime) {
        return expirationTime.before(new Date());
    }

    /**
     * 判断token是否已经失效
     */
    public boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 判断token是否可以被刷新
     */
    public boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 生成token的过期时间
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expire * 1000);
    }

    /**
     * 根据用户信息生成token
     */
    public String generateToken(String content) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_CONTENT, content);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }


    /**
     * 根据负责生成JWT的token
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 刷新token
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.info("JWT格式验证失败:{}",token);
        }
        return claims;
    }
    /**
     * 获取jwt发布时间
     */
    public Date getIssuedAtDateFromToken(String token) {
        return getTokenClaim(token).getIssuedAt();
    }


}
