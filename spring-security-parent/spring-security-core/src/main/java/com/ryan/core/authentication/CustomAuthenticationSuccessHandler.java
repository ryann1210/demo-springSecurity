package com.ryan.core.authentication;

import com.alibaba.fastjson.JSON;
import com.ryan.base.response.ResponseJson;
import com.ryan.core.properties.LoginResponseType;
import com.ryan.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证成功处理器
 * 1 决定响应json还是跳转页面 或者认证成功后进行其他处理
 */
@Component("customAuthenticationSuccessHandler")
//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        // 认证成功后 响应JSON字符串
        if (LoginResponseType.JSON.equals(securityProperties.getAuthentication().getLoginType())) {
            ResponseJson response = ResponseJson.ok("认证成功");
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(response.toJsonString());
        }
        // 重定向到上次请求的地址
        else {
            logger.info("authentication : " + JSON.toJSONString(authentication));
            super.onAuthenticationSuccess(httpServletRequest,httpServletResponse, authentication);
        }
    }
}
