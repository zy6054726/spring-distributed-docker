package com.distributed.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * oauth鉴权启动类
 * @program: spring-distributed-docker
 * @description: OauthApplication
 * @author: Mr.Zhang
 * @create: 2019-07-18 15:56
 */
@SpringBootApplication
@EnableAuthorizationServer
@EnableOAuth2Sso
public class OauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthApplication.class, args);
    }
}
