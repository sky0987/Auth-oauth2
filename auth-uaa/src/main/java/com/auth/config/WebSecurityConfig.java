package com.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean  //生成bean才可以注入spring容器中去访问 oauth2  认证管理器
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    private DaoAuthenticationProviderCustom daoAuthenticationProviderCustom;

    @Override  //重写了密码认证方式  统一了认证入口
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProviderCustom);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
       //密码为明文方式
          //  return NoOpPasswordEncoder.getInstance();
       return new BCryptPasswordEncoder();
    }

    //配置安全拦截机制
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/login.html").
                loginProcessingUrl("/login").
                successForwardUrl("/login-success").permitAll().
                and().authorizeRequests().antMatchers("/user/**")
                        .authenticated()
                                .anyRequest().permitAll();

        http
                .cors()
                .and().
                 csrf().disable();

    }



}
