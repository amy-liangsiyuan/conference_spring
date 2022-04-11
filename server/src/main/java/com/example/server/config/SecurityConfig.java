package com.example.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Amy
 * @date 2022-03-22 22:21
 * @description
 * @package com.example.server.config
 * @title
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //将SpringSecurity的拦截请求全部放行
        http.headers().frameOptions().disable();
        //Spring Security4默认是将'X-Frame-Options' 设置为 'DENY'
        http.authorizeRequests()
                .antMatchers("/**")
                .permitAll()
                .and()
                .csrf().disable();

    }
}
