package cn.siyue.platform.weixin.client.service;

import cn.siyue.platform.weixin.common.response.weixinmessagereply.WeixinMessageReplyResponse;

import java.util.List;

public interface MessageRedisService {


    public void saveMsgReplyList(List<WeixinMessageReplyResponse> list);

    public List<WeixinMessageReplyResponse> getMsgReplyList();

    public void delMsgReplyList();




}
