package com.ryan.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "ryan.security") // 对应yml里的ryan.security
public class SecurityProperties {

    // authentication对应yml的ryan.security.authentication
    private AuthenticationProperties authentication;

}
