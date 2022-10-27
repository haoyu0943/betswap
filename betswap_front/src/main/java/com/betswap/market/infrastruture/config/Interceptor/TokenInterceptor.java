package com.betswap.market.infrastruture.config.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.betswap.market.client.user.vo.CurrentUserVO;
import com.betswap.market.infrastruture.config.annotation.DisableToken;
import com.betswap.market.infrastruture.config.util.GetRequestJsonUtil;
import com.betswap.market.infrastruture.config.util.MyHttpServletRequestWrapper;
import com.betswap.market.infrastruture.user.dao.UserDao;
import com.betswap.market.infrastruture.utils.helper.token.LoginTokenHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SignatureException;

@Component
@Slf4j
@Deprecated
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private LoginTokenHelper loginTokenHelper;
    @Autowired
    private UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws SignatureException, IOException {
        /** 允许option请求通过 **/
        if(request.getMethod().toUpperCase().equals("OPTIONS")){
            return true;
        }
        /** 设置返回头跨域 **/
        crossDomain(request, response);

        /** 地址过滤 */
        String uri = request.getRequestURI() ;
        if(uri.contains("swagger")){
            return true;
        }

        /** DisableToken注解检验 **/
        if (handler instanceof HandlerMethod){
            HandlerMethod method = (HandlerMethod) handler;
            DisableToken auth = method.getMethod().getAnnotation(DisableToken.class);
            //System.out.println("TokenInterceptor:" +request.getRequestURI() +" || "+ method.getMethod());
            if(auth != null){
                return true;
            }
        }
        else {
            //System.out.println("TokenInterceptor:" +request.getRequestURI());
        }

        /** Token 验证，先在Header里找 */
        String token = request.getHeader(loginTokenHelper.getHeader());

        /** 如果不在Header就在Params里找 */
        if(StringUtils.isEmpty(token)){
            token = request.getParameter(loginTokenHelper.getHeader());
        }
        /** 如果不在Params就在Body里找 */
        else if(StringUtils.isEmpty(token)){
            //封装request
            MyHttpServletRequestWrapper myWrapper= new MyHttpServletRequestWrapper(request);
            //获取json数据
            JSONObject json = GetRequestJsonUtil.getRequestJsonObject(myWrapper);

            token = json.getString(loginTokenHelper.getHeader());
        }

        //Token为空
        if(StringUtils.isEmpty(token) || StringUtils.isEmpty(token)){
            throw new SignatureException(loginTokenHelper.getHeader()+ "或方法名为空");
        }

        try{
            if(loginTokenHelper.isTokenExpired(token)){
                throw new SignatureException(loginTokenHelper.getHeader() + "失效，请重新登录。");
            }
        }catch (Exception e){
            throw new SignatureException(loginTokenHelper.getHeader() + "验证失败，请重新登录。");
        }

        /** 从 token 设置 username */
        String userId = loginTokenHelper.getTokenSubject(token);
        if(!userDao.existsByUserId(userId))
            throw new SignatureException("用户不存在");
        CurrentUserVO userInfo = CurrentUserVO.builder().userId(loginTokenHelper.getTokenSubject(token)).build();
        request.setAttribute("currentUser", userInfo);

        return true;
    }

    public void crossDomain(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header, Authorization");
        response.setHeader("Access-Control-Expose-Headers", "Verify-Token");
    }
}
