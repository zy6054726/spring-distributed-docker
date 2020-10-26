package com.distributed.oauth.server.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServerOauth{
    private static final long serialVersionUID = 1002908916083328306L;

    private String uuid;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 资源id
     */
    private String resourceIds;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 范围
     */
    private String scpoe;

    /**
     * 授权类型
     */
    private String authorizedGrantTypes;

    /**
     * 令牌有效时长
     */
    private Long accessTokenValidity;

    /**
     * 刷新令牌时长
     */
    private Long refreshTokenValidity;

    /**
     * 自动允许
     */
    private String autoapprove;

}