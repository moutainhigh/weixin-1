package cn.siyue.platform.weixin.client.service;

import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.CreateCardReq;
import cn.siyue.platform.weixin.common.response.weixincardcoupon.GetCardCouponResponse;
import com.siyueli.platform.member.pojo.coupon.Coupon;
import com.siyueli.platform.member.pojo.coupon.CouponTask;
import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;

public interface CouponService {

    public CreateCardReq getCardReq(GetCardCouponResponse cardCoupon, Coupon coupon, CouponTask couponTask);
}
