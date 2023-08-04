package com.auth.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Collection;

@Configuration
public class TokenConfig {
    /**
     * 内存方式生成普通令牌
     * @return
     */
//    @Bean          //不使用内存的方式生成token
//     public TokenStore  tokenStore(){
//         return  new InMemoryTokenStore();
//     }

    /**
     * 秘钥签名生成jwt
     */
    private String SIGNING_KEY = "sky";


    @Bean
    public  TokenStore  tokenStore(){
        return  new JwtTokenStore(accessTokenConverter());
     }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }
}
