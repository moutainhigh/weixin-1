package cn.siyue.platform.weixin.client.service;

import java.io.IOException;
import java.util.List;

public interface BaseRedisService {

    public void checkTtl(String key);

    public void checkTtl(String key, int seconds);

    public void setAndExpireString(String key, String content);

    public void setAndExpireObject(String key, Object object) throws IOException;

    public void setAndExpireString(String key, String content, int seconds);

    public void setAndExpireObject(String key, Object object, int seconds) throws IOException;

    public <T> T getObject(String key, Class<T> clazz) throws IOException;

    public <T> List<T> getList(String key, Class<T> clazz) throws IOException;
}
