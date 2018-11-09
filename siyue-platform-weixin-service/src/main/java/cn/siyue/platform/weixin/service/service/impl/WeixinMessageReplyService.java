package cn.siyue.platform.weixin.service.service.impl;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.util.ResponseUtil;
import cn.siyue.platform.weixin.common.common.PageResponse;
import cn.siyue.platform.weixin.common.entity.WeixinMessageReply;
import cn.siyue.platform.weixin.common.request.weixinmessagereply.*;
import cn.siyue.platform.weixin.common.response.weixinmessagereply.WeixinMessageReplyResponse;
import cn.siyue.platform.weixin.service.mapper.WeixinMessageReplyMapper;
import cn.siyue.platform.weixin.service.service.WeixinMessageReplyServiceContract;
import cn.siyue.platform.weixin.service.util.ConvertUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 微信消息回复表 服务实现类
 * </p>
 *
 * @author Sipin ERP Development Team
 */
@Primary
@Service
public class WeixinMessageReplyService extends ServiceImpl<WeixinMessageReplyMapper, WeixinMessageReply> implements WeixinMessageReplyServiceContract {

    @Override
    public ResponseData getMessageReplyList(GetMessageReplyListRequest requestParam) {
        EntityWrapper<WeixinMessageReply> entityWrapper = new EntityWrapper<WeixinMessageReply>();
        if (requestParam.getType() != null) {
            entityWrapper.eq("type", requestParam.getType());
        }
        Page<WeixinMessageReply> page = new Page<WeixinMessageReply>(requestParam.getPage(), requestParam.getSize());
        Page<WeixinMessageReply> pageResult = selectPage(page, entityWrapper);
        if (pageResult != null) {
            List<WeixinMessageReply> list = pageResult.getRecords();
            List<WeixinMessageReplyResponse> resultList = null;
            try {
                resultList = ConvertUtil.convertList(WeixinMessageReplyResponse.class, list);
                PageResponse<WeixinMessageReplyResponse> pageResponse = ConvertUtil.getPageResponse(pageResult, resultList);
                return ResponseUtil.success(pageResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ResponseUtil.fail();
    }

    @Override
    public ResponseData addMessageReply(AddMessageReplyRequest requestParam) {
        LocalDateTime now = LocalDateTime.now();

        WeixinMessageReply weixinMessageReply = new WeixinMessageReply();
        BeanUtils.copyProperties(requestParam, weixinMessageReply);
        weixinMessageReply.setUpdateAt(now);
        weixinMessageReply.setCreateAt(now);
        weixinMessageReply.setStatus(1);
        insertAllColumn(weixinMessageReply);
        return ResponseUtil.success();
    }

    @Override
    public ResponseData updateMessageReply(UpdateMessageReplyRequest requestParam) {
        WeixinMessageReply weixinMessageReply = selectById(requestParam.getId());
        if (weixinMessageReply != null) {
            BeanUtils.copyProperties(requestParam, weixinMessageReply);
            weixinMessageReply.setUpdateAt(LocalDateTime.now());
            updateAllColumnById(weixinMessageReply);
            return ResponseUtil.success();
        }

        return ResponseUtil.fail();
    }

    @Override
    public ResponseData getMessageReply(GetMessageReplyRequest requestParam) {
        WeixinMessageReply weixinMessageReply = selectById(requestParam.getId());
        if (weixinMessageReply != null) {
            WeixinMessageReplyResponse resp = new WeixinMessageReplyResponse();
            BeanUtils.copyProperties(weixinMessageReply, resp);
            return ResponseUtil.success(resp);
        }

        return ResponseUtil.fail();
    }

    @Override
    public ResponseData updateMessageReplyStatus(UpdateMessageReplyStatusRequest requestParam) {
        WeixinMessageReply weixinMessageReply = selectById(requestParam.getId());
        if (weixinMessageReply != null) {
            weixinMessageReply.setStatus(requestParam.getStatus());
            weixinMessageReply.setUpdateAt(LocalDateTime.now());
            updateAllColumnById(weixinMessageReply);
            return ResponseUtil.success();
        }
        return ResponseUtil.fail();
    }

    @Override
    public ResponseData deleteMessageReply(DeleteMessageReplyRequest requestParam) {
        deleteById(requestParam.getId());
        return ResponseUtil.success();
    }
}
