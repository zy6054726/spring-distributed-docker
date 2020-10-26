package com.distributed.oauth.server.controller;

import cn.hutool.core.util.StrUtil;
import com.distributed.common.util.ReturnResult;
import com.distributed.common.util.enums.Flag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: Mr.zhang
 * @Date: 2020/10/2 17:55
 */
@Slf4j
@RestController
@RequestMapping("/oauth")
public class AuthController {
    @Autowired
    private TokenEndpoint tokenEndpoint;

    @GetMapping("/token")
    public ReturnResult getAccessToken(Principal principal, @RequestParam Map<String, String> parameters)
            throws HttpRequestMethodNotSupportedException {
        if (StrUtil.equals("access_token", parameters.get("grant_type"))) {
            parameters.put("username", parameters.get("client_id"));
            parameters.put("password", parameters.get("client_secret"));
            parameters.put("grant_type", "password");
        }
        if (StrUtil.equals("password", parameters.get("grant_type"))) {
            parameters.put("username", parameters.get("client_id"));
            parameters.put("password", parameters.get("client_secret"));
        }
        return custom(tokenEndpoint.getAccessToken(principal, parameters).getBody());
    }

    @PostMapping("/token")
    public ReturnResult postAccessToken(Principal principal, @RequestParam Map<String, String> parameters)
            throws HttpRequestMethodNotSupportedException {
        if (StrUtil.equals("password", parameters.get("grant_type"))) {
            parameters.put("username", parameters.get("client_id"));
            parameters.put("password", parameters.get("client_secret"));
        }
        if (StrUtil.equals("access_token", parameters.get("grant_type"))) {
            parameters.put("username", parameters.get("client_id"));
            parameters.put("password", parameters.get("client_secret"));
            parameters.put("grant_type", "password");
        }
        return custom(tokenEndpoint.postAccessToken(principal, parameters).getBody());
    }

    /**
     * 自定义返回格式
     *
     * @param accessToken
     * @return
     */
    private ReturnResult custom(OAuth2AccessToken accessToken) {
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
        Map<String, Object> data = new LinkedHashMap(token.getAdditionalInformation());
        if (token.getRefreshToken() != null) {
            data.put("refreshToken", token.getRefreshToken().getValue());
        }
        return new ReturnResult(Flag.SUCCESS, data);
    }
}
