package com.distributed.oauth.server.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.distributed.oauth.server.mapper.ServerOauthMapper;
import com.distributed.oauth.server.model.ServerOauth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author: Mr.zhang
 * @Date: 2020/2/23 11:49
 *
 */
@Service
@Slf4j
public class ServerOauthServiceImpl implements ClientDetailsService {


    @Resource
    private ServerOauthMapper serverOauthMapper;

    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        ServerOauth serverOauth = serverOauthMapper.findByClientId(s);
        if (Objects.isNull(serverOauth)) {
            throw new RuntimeException("该client_id不存在");
        }

        BaseClientDetails clientDetails = new BaseClientDetails();
        //客户端(client)id
        clientDetails.setClientId(serverOauth.getClientId());
        //客户端所能访问的资源id集合
//        clientDetails.setResourceIds();
//客户端(client)的访问密匙
        clientDetails.setClientSecret(SecureUtil.md5(serverOauth.getClientSecret()));
//        客户端支持的grant_type授权类型
        clientDetails.setAuthorizedGrantTypes(Arrays.asList(serverOauth.getAuthorizedGrantTypes().split(",")));
        //客户端申请的权限范围
        clientDetails.setScope(Arrays.asList(serverOauth.getScpoe().split(",")));
        Long accessTokenValidity = serverOauth.getAccessTokenValidity();
        if (accessTokenValidity != null && accessTokenValidity > 0) {
            //设置token的有效期，不设置默认12小时
            clientDetails.setAccessTokenValiditySeconds(accessTokenValidity.intValue());
        }
        Long refreshTokenValidity = serverOauth.getRefreshTokenValidity();
        if (refreshTokenValidity != null && refreshTokenValidity > 0) {
            //设置刷新token的有效期，不设置默认30天
            clientDetails.setRefreshTokenValiditySeconds(refreshTokenValidity.intValue());
        }
//        Set<String> set = new HashSet<>();
//        set.add("www.baidu.com");
//        clientDetails.setRegisteredRedirectUri(set);
        clientDetails.isAutoApprove(serverOauth.getAutoapprove());
        log.info("clientId是：{}", s);

        return clientDetails;
    }
}

