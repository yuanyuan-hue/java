package com.qdc.demoeurekaauth_server.config;


import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class Oauth2AuthoriztionServerConfigureation extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private DruidDataSource druidDataSource;

//    AuthorizationServerSecurityConfigurer：配置令牌端点的安全约束
//    设置了只有ROLE_TRUSTED_CLIENT权限的客户端才可以进行安全验证
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        设置检查token是否有效
    security.checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(new JdbcClientDetailsService(druidDataSource));
    }

//    生成令牌
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.userDetailsService(userDetailsService);
        TokenStore tokenStore=new JdbcTokenStore(druidDataSource);
        DefaultTokenServices tokenServices=new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(new JdbcClientDetailsService(druidDataSource));
        tokenServices.setAccessTokenValiditySeconds(38000);
        endpoints.tokenServices(tokenServices);
//        数据库管理授权码
        endpoints.authorizationCodeServices(new JdbcAuthorizationCodeServices(druidDataSource));
        ApprovalStore approvalStore=new JdbcApprovalStore(druidDataSource);
        endpoints.approvalStore(approvalStore);
    }
}
