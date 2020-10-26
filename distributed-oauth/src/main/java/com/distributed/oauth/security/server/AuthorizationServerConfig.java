package com.distributed.oauth.security.server;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.distributed.oauth.security.MyPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.Map;

/**
 * 认证服务
 *
 * @author: Mr.zhang
 * @Date: 2020/2/20 15:58
 */
@Configuration
@EnableAuthorizationServer
@Order(100)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("serverOauthServiceImpl")
    private ClientDetailsService clientDetailsService;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

//    @Autowired
//    @Qualifier("userServiceImpl")
//    private UserDetailsService userDetailsService;
    /**
     * 认证管理器
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyPasswordEncoder passwordEncoder;


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                // 开启/oauth/token_key验证端口无权限访问
                .tokenKeyAccess("permitAll()")
                // 开启/oauth/check_token验证端口认证权限访问
                .checkTokenAccess("isAuthenticated()")
                .passwordEncoder(passwordEncoder).allowFormAuthenticationForClients();
//                .addTokenEndpointAuthenticationFilter(new MyBaseicAuthenticationFilter());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()  // 使用in-memory存储
                .withClient("app")    //client_id用来标识客户的Id  客户端1
                .resourceIds("app")
                .authorizedGrantTypes("refresh_token","client_credentials")  //允许授权类型   客户端授权模式
                .scopes("all")  //允许授权范围
                .authorities("oauth2")  //客户端可以使用的权限
                .secret(SecureUtil.md5("a119ed01622927d54cd67e10cbb9f7ad"))  //secret客户端安全码
                .accessTokenValiditySeconds(100)   //token 时间秒
                .refreshTokenValiditySeconds(10000);//刷新token 时间 秒
//                .and()
//                .withClient(CLIEN_ID_TWO) //client_id用来标识客户的Id  客户端 2
//                .resourceIds(RESOURCE_ID)00
//                .authorizedGrantTypes(GRANT_TYPE_PASSWORD, REFRESH_TOKEN)   //允许授权类型  密码授权模式
//                .scopes(SCOPE_READ,SCOPE_WRITE) //允许授权范围
//                .authorities("oauth2") //客户端可以使用的权限
//                .secret(SecureUtil.md5("secret"))  //secret客户端安全码
//                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)    //token 时间秒
//                .refreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS); //刷新token 时间 秒
//        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //指定认证管理器
        endpoints.authenticationManager(authenticationManager)
//                .userDetailsService(userDetailsService)
                .allowedTokenEndpointRequestMethods(HttpMethod.POST, HttpMethod.GET,HttpMethod.DELETE,HttpMethod.PUT)
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter())
//                .tokenEnhancer(tokenEnhancerChain)
                .tokenServices(defaultTokenServices())
                .reuseRefreshTokens(true);
    }

    /**
     * 注入自定义token生成方式（jwt）
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new TokenEnhancer() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
//                Object obj = oAuth2Authentication.getPrincipal();
                Map<String, Object> maps = MapUtil.newHashMap();

                maps.put("access_token", oAuth2AccessToken.getValue());
//                maps.put("token_type", oAuth2AccessToken.getTokenType());
//                maps.put("scope", oAuth2AccessToken.getScope());
                maps.put("expire_in", oAuth2AccessToken.getExpiresIn());
//
                ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(maps);
                return oAuth2AccessToken;
            }
        };


//        return new JwtTokenEnhancer();
    }

    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        // 这里如果设置为false则不能更新refresh_token，如果需要刷新token的功能需要设置成true
        tokenServices.setSupportRefreshToken(true);
        // 设置上次RefreshToken是否还可以使用 默认为true
        tokenServices.setReuseRefreshToken(false);
//        // token有效期自定义设置，默认12小时
//        tokenServices.setAccessTokenValiditySeconds(60 * 60 * 12);
//        // refresh_token默认30天
//        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);
        tokenServices.setTokenEnhancer(tokenEnhancer());
        return tokenServices;
    }

    /**
     * 对Jwt签名时，增加一个密钥
     * JwtAccessTokenConverter：对Jwt来进行编码以及解码的类
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        //测试用,资源服务使用相同的字符达到一个对称加密的效果,生产时候使用RSA非对称加密方式
        accessTokenConverter.setSigningKey("jwt_zy");
        return accessTokenConverter;
    }

    /**
     * 自定义token存储器，存入Redis中
     */
    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }
}
