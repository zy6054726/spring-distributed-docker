package com.distributed.oauth.config;

import com.distributed.common.util.ReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.LinkedHashMap;

/**
 * @author: Mr.zhang
 * @Date: 2020/2/28 14:20
 */
@Slf4j
@Order(5)
@ControllerAdvice
public class MyResponseBodyAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        if (o instanceof LinkedHashMap) {
            LinkedHashMap<String, Object> stringObjectLinkedHashMap = (LinkedHashMap<String, Object>) o;
            ReturnResult returnResult = new ReturnResult();
            Integer code = (Integer) stringObjectLinkedHashMap.get("status");
            returnResult.setCode(code);
            returnResult.setMessage(stringObjectLinkedHashMap.get("message").toString());
            return returnResult;
        }
        return o;
    }
}
