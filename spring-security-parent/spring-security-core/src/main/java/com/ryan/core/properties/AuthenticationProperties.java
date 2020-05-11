package com.ryan.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Data
@EnableConfigurationProperties(SecurityProperties.class)
public class AuthenticationProperties {

    // 对应yml里的ryan.security.authentication.loginPage
    private String loginPage = "/login/page";
    private String loginProcessingUrl = "/login/form";
    private String usernameParameter = "name";
    private String passwordParameter = "pwd";
    private String[] staticPaths = {"/dist/**", "/modules/**", "/plugins/**"};

}
