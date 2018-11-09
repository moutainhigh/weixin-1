package cn.siyue.platform.weixin.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@ConfigurationProperties(prefix = "weixin.mp")
@Data
public class WeixinConfig {

    private String appId;
    private String secret;
    private String token;
    private String aesKey;

    private String logoUrl;

    private String servicePhone;

}
