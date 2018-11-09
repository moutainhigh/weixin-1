/*
 * (C) Copyright 2018 Siyue Holding Group.
 */
package cn.siyue.platform.weixin.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 微信服务类入口
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"cn.siyue.platform.weixin.service.*","cn.siyue.platform.*"})
@MapperScan("cn.siyue.platform.weixin.service.mapper")
public class WeixinServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(WeixinServiceApplication.class);
  }
}
