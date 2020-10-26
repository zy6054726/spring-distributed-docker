package com.distributed.oauth.security;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author: Mr.zhang
 * @Date: 2020/2/23 21:01
 */
@Service
public class MyPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return SecureUtil.md5(charSequence.toString());
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return StrUtil.equals(s, SecureUtil.md5(charSequence.toString()));
    }
}
