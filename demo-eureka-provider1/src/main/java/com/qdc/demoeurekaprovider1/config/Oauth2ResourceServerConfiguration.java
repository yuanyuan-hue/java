//package com.qdc.demoeurekaprovider1.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
//import sun.security.krb5.Config;
//
////去认证服务器验证令牌有效
//@Configuration
//public class Oauth2ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//private static final String URL="http://localhost:8081/oauth/check_token";
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        //        设置验证Token的方法，即使用test和123456的客户端身份去URL验证token
//        RemoteTokenServices tokenService=new RemoteTokenServices();
//        tokenService.setCheckTokenEndpointUrl(URL);
//        tokenService.setClientId("test");
//        tokenService.setClientSecret("123456");
//
//        resources.tokenServices(tokenService);
//        resources.resourceId("userall").stateless(true);
//    }
//}
