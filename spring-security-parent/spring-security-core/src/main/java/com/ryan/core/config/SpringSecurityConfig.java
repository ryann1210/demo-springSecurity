package com.ryan.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity // 开启SpringSecurity过滤链
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    Logger logger = LoggerFactory.getLogger(SpringSecurityConfig.class);

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证管理器：
     * 1、认证信息提供方式（用户名、密码、当前用户的资源权限）
     * 2、可采用内存存储方式，也可能采用数据库方式等
     *
     * @param
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 数据库存储的密码必须是加密后的，不然会报错
        // There is no PasswordEncoder mapped for the id "null"
        String password = passwordEncoder().encode("123123");
        logger.info("加密后的密码哟：" + password);
        auth.inMemoryAuthentication()
            .withUser("ryan")
            .password(password)
            .authorities("admin");
    }

    /**
     * 资源权限配置（过滤器链）:
     * 1、被拦截的资源
     * 2、资源所对应的角色权限
     * 3、定义认证方式：httpBasic 、httpForm
     * 4、定制登录页面、登录请求地址、错误处理方式
     * 5、自定义 spring security 过滤器
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // httpBasic弹出窗口认证
        // formLogin表单认证方式
        http.formLogin()
            .loginPage("/login/page") // 指定表单登录页面
            .loginProcessingUrl("/login/form") // 指定表单登录提交接口
            .usernameParameter("name") // 指定用户名字段名 默认username
            .passwordParameter("pwd") // 指定密码字段名 默认password
            .and()
            .authorizeRequests() // 认证请求
            .antMatchers("/login/page").permitAll() // 放行/login/page请求
            .anyRequest().authenticated() // 所有进入应用的HTTP请求都要进行认证
        ;
    }

    /**
     * 一般针对静态资源放行
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/dist/**", "/modules/**", "/plugins/**");
    }
}
