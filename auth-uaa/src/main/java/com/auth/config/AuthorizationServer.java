package com.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter{


    /**
     * 客户端的配置
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
        clients.inMemory()// 使用in-memory存储
                .withClient("cc")// client_id
                .secret("cc")//客户端密钥
                .secret(new BCryptPasswordEncoder().encode("cc"))//客户端密钥
                .resourceIds("Auth-oauth2")//资源列表
                .authorizedGrantTypes( "authorization_code","password","refresh_token","client_credentials","implicit")// 该client允许的授权类型authorization_code,password,refresh_token,implicit,client_credentials
                .scopes("all")// 允许的授权范围
                .autoApprove(false)//false跳转到授权页面
                .redirectUris("http://www.baidu.com");
    }

    /**
     * 授权码的储存方式  内存模式
     * @return
     */
    @Bean
    public   AuthorizationCodeServices  authorizationCodeServices(){
        return   new InMemoryAuthorizationCodeServices();
    }


    /**
     * 令牌配置服务
     */
    @Autowired
    private TokenStore  tokenStore;
    @Autowired
    private ClientDetailsService  clientDetailsService;

    @Autowired  //不使用内存的方式去设置token
    private JwtAccessTokenConverter accessTokenConverter;


    @Bean
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices service=new DefaultTokenServices();
        service.setSupportRefreshToken(true);//支持刷新令牌
        service.setTokenStore(tokenStore);//令牌存储策略

        service.setClientDetailsService(clientDetailsService); //客户端信息服务

       //不使用内存的方式生成token
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
        service.setTokenEnhancer(tokenEnhancerChain);

        service.setAccessTokenValiditySeconds(7200); // 令牌默认有效期2小时
        service.setRefreshTokenValiditySeconds(259200); // 刷新令牌默认有效期3天
        return service;
    }



    /**
     * 令牌端点的访问配置
     */
    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private  AuthorizationCodeServices  authorizationCodeServices;
   // @Autowired
   // private  AuthorizationServerTokenServices  authorizationServerTokenServices;
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .authorizationCodeServices(authorizationCodeServices) //授权码模式需要  授权码的储存方式
                .authenticationManager(authenticationManager)//认证管理器
                .tokenServices(tokenService())//令牌管理服务
                //.tokenServices(authorizationServerTokenServices) //两种方式  可以bean自动注入的方式
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }


    /**
     * 令牌的安全约束
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security){
        security
                .tokenKeyAccess("permitAll()")                    //oauth/token_key是公开
                .checkTokenAccess("permitAll()")                  //oauth/check_token公开
                .allowFormAuthenticationForClients();				//表单认证（申请令牌）
    }

}