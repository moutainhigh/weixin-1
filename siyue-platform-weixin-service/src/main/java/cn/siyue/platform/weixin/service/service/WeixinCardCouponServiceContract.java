package cn.siyue.platform.weixin.service.service;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.weixin.common.common.PageResponse;
import cn.siyue.platform.weixin.common.entity.WeixinCardCoupon;
import cn.siyue.platform.weixin.common.request.weixincardcoupon.*;
import cn.siyue.platform.weixin.common.response.weixincardcoupon.GetCardCouponResponse;
import cn.siyue.platform.weixin.common.response.weixincardcoupon.SearchCardCouponResponse;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 微信卡券表 服务类
 * </p>
 *
 * @author Sipin ERP Development Team
 */
public interface WeixinCardCouponServiceContract extends IService<WeixinCardCoupon> {


    ResponseData add(AddCardCouponRequest requestParam);

    ResponseData update(UpdateCardCouponRequest requestParam);

    ResponseData<GetCardCouponResponse> get(GetCardCouponRequest requestParam);

    ResponseData<PageResponse<GetCardCouponResponse>> getList(GetCardCouponListRequest requestParam);

    ResponseData publish(PublishCardRequest requestParam);


    ResponseData<PageResponse<SearchCardCouponResponse>> search(SearchCardCouponRequest requestParam);
}
