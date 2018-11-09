package cn.siyue.platform.weixin.client.service.impl;

import cn.siyue.platform.weixin.client.constant.WeixinClientConstant;
import cn.siyue.platform.weixin.client.service.BaseRedisService;
import cn.siyue.platform.weixin.client.service.MessageRedisService;
import cn.siyue.platform.weixin.common.response.weixinmessagereply.WeixinMessageReplyResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MessageRedisServiceImpl extends BaseRedisServiceImpl implements MessageRedisService, BaseRedisService {

    private static final String REDIS_KEY_MSG_REPLY_LIST = WeixinClientConstant.REDIS_KEY_PREFIX + "msgReplyList";

    @Override
    public void saveMsgReplyList(List<WeixinMessageReplyResponse> list) {
        if (list != null && list.size() > 0) {
            try {
                setAndExpireObject(REDIS_KEY_MSG_REPLY_LIST, list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<WeixinMessageReplyResponse> getMsgReplyList() {
        try {
            List<WeixinMessageReplyResponse> list = getList(REDIS_KEY_MSG_REPLY_LIST, WeixinMessageReplyResponse.class);
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            checkTtl(REDIS_KEY_MSG_REPLY_LIST);
        }
        return null;
    }

    @Override
    public void delMsgReplyList() {
        jedisCluster.del(REDIS_KEY_MSG_REPLY_LIST);
    }
}
