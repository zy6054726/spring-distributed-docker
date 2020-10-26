package com.distributed.common.uuid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 百度雪花算法属性配置
 */
@SpringBootApplication
@EnableDiscoveryClient
public class BaiduUidApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaiduUidApplication.class, args);
    }
}
