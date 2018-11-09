package cn.siyue.platform.weixin.client.aspect;

import cn.siyue.platform.httplog.aspect.LogAspect;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class WeixinClientLogAspect extends LogAspect {
    @Override
    public Long getUserId() {
        return null;
    }

    @Override
    public String getSystemName() {
        return "Member client";
    }
}
