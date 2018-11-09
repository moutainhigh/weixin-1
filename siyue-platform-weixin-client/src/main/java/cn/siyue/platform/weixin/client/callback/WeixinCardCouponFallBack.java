package cn.siyue.platform.weixin.client.callback;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.weixin.client.service.weixin.WeixinCardCouponClientService;
import cn.siyue.platform.weixin.common.common.PageResponse;
import cn.siyue.platform.weixin.common.request.weixincardcoupon.*;
import cn.siyue.platform.weixin.common.response.weixincardcoupon.GetCardCouponResponse;
import cn.siyue.platform.weixin.common.response.weixincardcoupon.SearchCardCouponResponse;
import org.springframework.stereotype.Component;

@Component
public class WeixinCardCouponFallBack extends BaseServiceFallBack implements WeixinCardCouponClientService {
    @Override
    public ResponseData add(AddCardCouponRequest requestParam) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData update(UpdateCardCouponRequest requestParam) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData<GetCardCouponResponse> get(GetCardCouponRequest requestParam) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData<PageResponse<GetCardCouponResponse>> getList(GetCardCouponListRequest requestParam) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData publish(PublishCardRequest requestParam) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData updateStatus(Long id, Integer status) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData updateStock(Long id, Integer stockQty) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData<PageResponse<SearchCardCouponResponse>> search(SearchCardCouponRequest requestParam) {
        return getDownGradeResponse();
    }
}
