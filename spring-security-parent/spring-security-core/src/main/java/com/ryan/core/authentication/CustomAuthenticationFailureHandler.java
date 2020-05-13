package com.ryan.core.authentication;

import com.ryan.base.response.ResponseJson;
import com.ryan.core.properties.LoginResponseType;
import com.ryan.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理失败认证
 */
@Component("customAuthenticationFailureHandler")
//public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {
        // 认证失败 响应json字符串
        if (LoginResponseType.JSON.equals(securityProperties.getAuthentication().getLoginType())) {

            ResponseJson responseJson = ResponseJson.build(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(responseJson.toJsonString());
        }
        // 重定向
        else {
            // 重定向到认证页面 加上error参数
            super.setDefaultFailureUrl(securityProperties.getAuthentication().getLoginPage() + "?error");
            super.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
        }
    }
}
