package cn.siyue.platform.weixin.service.service.impl;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.util.ResponseUtil;
import cn.siyue.platform.weixin.common.common.PageResponse;
import cn.siyue.platform.weixin.common.common.weixincardcoupon.CommonCardCouponVo;
import cn.siyue.platform.weixin.common.constants.WeixinConstant;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.CreateCardReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.cardtype.MemberCard;
import cn.siyue.platform.weixin.common.dto.weixincardcoupon.SearchCardCouponDto;
import cn.siyue.platform.weixin.common.entity.WeixinCardCoupon;
import cn.siyue.platform.weixin.common.request.weixincardcoupon.*;
import cn.siyue.platform.weixin.common.response.weixincardcoupon.GetCardCouponResponse;
import cn.siyue.platform.weixin.common.response.weixincardcoupon.SearchCardCouponResponse;
import cn.siyue.platform.weixin.service.mapper.WeixinCardCouponMapper;
import cn.siyue.platform.weixin.service.service.WeixinCardCouponServiceContract;
import cn.siyue.platform.weixin.service.util.ConvertUtil;
import cn.siyue.platform.weixin.service.util.JsonUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 微信卡券表 服务实现类
 * </p>
 *
 * @author Sipin ERP Development Team
 */
@Primary
@Service
public class WeixinCardCouponService extends ServiceImpl<WeixinCardCouponMapper, WeixinCardCoupon> implements WeixinCardCouponServiceContract {

    @Autowired
    private WeixinCardCouponMapper weixinCardCouponMapper;

    @Override
    public ResponseData add(AddCardCouponRequest requestParam) {
        WeixinCardCoupon entity = new WeixinCardCoupon();
        BeanUtils.copyProperties(requestParam, entity);

        setOtherInfo(entity, requestParam, requestParam.getExt(), requestParam.getTaskTypeId());

        Date now = new Date();
        entity.setCreateAt(now);
        entity.setUpdateAt(now);
        entity.setStatus(WeixinConstant.CardCouponConstant.STATUS_READY_PUBLISH);
        insertAllColumn(entity);
        return ResponseUtil.success();
    }

    @Override
    public ResponseData update(UpdateCardCouponRequest requestParam) {
        WeixinCardCoupon entity = selectById(requestParam.getId());
        BeanUtils.copyProperties(requestParam, entity);

        if (StringUtils.isEmpty(requestParam.getCoverImageUrl()) || !requestParam.getCoverImageUrl().equals(entity.getCoverImageUrl())) {
            entity.setCoverMaterialUrl(null);
        }

        setOtherInfo(entity, requestParam, requestParam.getExt(), requestParam.getTaskTypeId());

        Date now = new Date();
        entity.setUpdateAt(now);
        updateAllColumnById(entity);
        return ResponseUtil.success();
    }

    private CreateCardReq getCard(Integer taskTypeId, ObjectNode ext) {
        if (cn.siyue.platform.util.JsonUtil.isNullNode(ext))
            return null;

        // 会员卡
        if (WeixinConstant.CouponTaskConstant.TYPE_ID_MEMBER_CARD.equals(taskTypeId)) {
            CreateCardReq req = new CreateCardReq();
            JsonNode cardNode = ext.path("card");
            if (!cn.siyue.platform.util.JsonUtil.isNullNode(cardNode)) {
                MemberCard memberCard = null;
                memberCard = jsonToObject(cardNode, MemberCard.class);
                req.setCard(memberCard);
            }
            return req;
        }

        return null;
    }



    private <T> T jsonToObject(JsonNode jsonNode, Class<T> clazz) {
        try {
            return JsonUtil.jsonToObject(jsonNode, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void setOtherInfo(WeixinCardCoupon entity, CommonCardCouponVo requestParam, ObjectNode nodeExt, Integer taskTypeId) {
        CreateCardReq cardExt = getCard(taskTypeId, nodeExt);


        if (requestParam.getTimeLimitList() != null && requestParam.getTimeLimitList().size() > 0) {
            try {
                String timeLimit = JsonUtil.toJsonString(requestParam.getTimeLimitList());
                entity.setTimeLimit(timeLimit);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (cardExt != null) {
            String ext = null;
            try {
                ext = JsonUtil.toJsonString(cardExt);
                entity.setExt(ext);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public ResponseData get(GetCardCouponRequest requestParam) {
        WeixinCardCoupon entity = null;
        if (requestParam.getId() != null) {
            entity = selectById(requestParam.getId());
        } else if (StringUtils.isNotEmpty(requestParam.getCardId())) {
            EntityWrapper<WeixinCardCoupon> entityWrapper = new EntityWrapper<WeixinCardCoupon>();
            entityWrapper.eq("card_id", requestParam.getCardId());
            entity = selectOne(entityWrapper);
        }

        GetCardCouponResponse cardCouponVo = null;
        if (entity != null) {
            cardCouponVo = new GetCardCouponResponse();
            BeanUtils.copyProperties(entity, cardCouponVo);
            convertCommonInfo(cardCouponVo, entity);
        }

        return ResponseUtil.success(cardCouponVo);
    }

    private void convertCommonInfo(CommonCardCouponVo commonVo, WeixinCardCoupon entity) {
        commonVo.setTimeLimitList(getTimeLimitList(entity.getTimeLimit()));
        ObjectNode cardExt = getCardExt(entity.getExt());
        commonVo.setExt(cardExt);
    }

    private ObjectNode getCardExt(String ext) {
        if (StringUtils.isNotEmpty(ext)) {
            try {
                ObjectNode extNode = (ObjectNode)JsonUtil.parseJson(ext);
                return extNode;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private List<TimeLimitVo> getTimeLimitList(String timeLimit) {
        if (StringUtils.isNotEmpty(timeLimit)) {
            try {
                List<TimeLimitVo> list = JsonUtil.jsonToObject(timeLimit, List.class, TimeLimitVo.class);
                return list;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ResponseData<PageResponse<GetCardCouponResponse>> getList(GetCardCouponListRequest requestParam) {
        EntityWrapper<WeixinCardCoupon> entityWrapper = new EntityWrapper<WeixinCardCoupon>();
        Page<WeixinCardCoupon> page = new Page<WeixinCardCoupon>(requestParam.getPage(), requestParam.getSize());
        Page<WeixinCardCoupon> pageResult = selectPage(page, entityWrapper);
        try {
            List<GetCardCouponResponse> list = null;
            if (pageResult != null && pageResult.getRecords() != null && pageResult.getRecords().size() > 0) {
                list = new ArrayList<GetCardCouponResponse>();
                for (WeixinCardCoupon entity : pageResult.getRecords()) {
                    GetCardCouponResponse cardCouponVo = new GetCardCouponResponse();
                    BeanUtils.copyProperties(entity, cardCouponVo);
                    convertCommonInfo(cardCouponVo, entity);

                    list.add(cardCouponVo);
                }
            }
            PageResponse<GetCardCouponResponse> pageResponse = ConvertUtil.getPageResponse(pageResult, list);
            return ResponseUtil.success(pageResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseUtil.fail();
    }

    @Override
    public ResponseData publish(PublishCardRequest requestParam) {
        WeixinCardCoupon entity = selectById(requestParam.getId());
        entity.setCardId(requestParam.getCardId());
        entity.setCoverMaterialUrl(requestParam.getCoverMaterialUrl());
        entity.setStatus(WeixinConstant.CardCouponConstant.STATUS_CHECKING);
        updateAllColumnById(entity);
        return ResponseUtil.success();
    }

    @Override
    public ResponseData<PageResponse<SearchCardCouponResponse>> search(SearchCardCouponRequest requestParam) {
        Pagination page = new Pagination(requestParam.getPage(), requestParam.getSize());

        List<SearchCardCouponDto> list = weixinCardCouponMapper.searchCoupon(requestParam, page);
        PageResponse<SearchCardCouponResponse> pageResponse = null;
        if (list != null && list.size() > 0) {
            List<SearchCardCouponResponse> respList = new ArrayList<SearchCardCouponResponse>();
            for (SearchCardCouponDto cardCoupon : list) {
                SearchCardCouponResponse cardCouponVo = new SearchCardCouponResponse();
                BeanUtils.copyProperties(cardCoupon, cardCouponVo);
                convertCommonInfo(cardCouponVo, cardCoupon);

                respList.add(cardCouponVo);
            }


            pageResponse = new PageResponse<SearchCardCouponResponse>();
            pageResponse.setRecords(respList);
            pageResponse.setPages(page.getPages());
            pageResponse.setTotal(page.getTotal());
            pageResponse.setCurrent(page.getCurrent());
            pageResponse.setSize(page.getSize());
        }
        return ResponseUtil.success(pageResponse);
    }
}
