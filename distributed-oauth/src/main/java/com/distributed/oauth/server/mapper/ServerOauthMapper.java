package com.distributed.oauth.server.mapper;

import com.distributed.oauth.server.model.ServerOauth;
import org.apache.ibatis.annotations.Param;

public interface ServerOauthMapper {

    ServerOauth findByClientId(@Param("clientId") String clientId);

    int isPresence(@Param("client_id") String client_id);

    int insert(ServerOauth serverOauth);

}