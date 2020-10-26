package com.distributed.oauth.test;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;

/**
 * @author: Mr.zhang
 * @Date: 2020/10/2 17:42
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//@Slf4j
public class Test {

//    @Autowired
//    private TokenEndpoint tokenEndpoint;

    public static void main(String[] args){
        String key = RandomUtil.randomString(8);
        System.out.println(key);
        String ls = SecureUtil.des(key.getBytes()).encryptHex("zhangyong".getBytes());
        System.out.println("临时：" + ls);
        String ll = SecureUtil.des(key.getBytes()).decryptStr(ls);
        System.out.println("最终："+ll);



//        Map<String, String> parameters = MapUtil.newHashMap();
//        if (StrUtil.equals("access_token", parameters.get("grant_type"))) {
//            parameters.put("username", parameters.get("client_id"));
//            parameters.put("password", parameters.get("client_secret"));
//            parameters.put("grant_type", "password");
//        }
//        if (StrUtil.equals("password", parameters.get("grant_type"))) {
//            parameters.put("username", parameters.get("client_id"));
//            parameters.put("password", parameters.get("client_secret"));
//        }
//        Principal principal = new Principal() {
//            @Override
//            public String getName() {
//                return null;
//            }
//        };
//         custom(tokenEndpoint.getAccessToken(principal, parameters).getBody());
    }

//    /**
//     * 自定义返回格式
//     *
//     * @param accessToken
//     * @return
//     */
//    private ReturnResult custom(OAuth2AccessToken accessToken) {
//        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
//        Map<String, Object> data = new LinkedHashMap(token.getAdditionalInformation());
//        if (token.getRefreshToken() != null) {
//            data.put("refreshToken", token.getRefreshToken().getValue());
//        }
//        return new ReturnResult(Flag.SUCCESS, data);
//    }

}
