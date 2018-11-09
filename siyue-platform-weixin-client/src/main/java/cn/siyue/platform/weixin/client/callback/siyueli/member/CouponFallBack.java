package cn.siyue.platform.weixin.client.callback.siyueli.member;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.weixin.client.callback.BaseServiceFallBack;
import cn.siyue.platform.weixin.client.service.siyueli.member.CouponClientService;
import com.siyueli.platform.member.request.coupon.AddCouponRequest;
import com.siyueli.platform.member.request.coupon.GetCouponListRequest;
import com.siyueli.platform.member.request.coupon.GetCouponRequest;
import com.siyueli.platform.member.request.coupon.UpdateCouponRequest;
import org.springframework.stereotype.Component;

@Component
public class CouponFallBack extends BaseServiceFallBack implements CouponClientService {


    @Override
    public ResponseData add(AddCouponRequest requestParam) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData update(UpdateCouponRequest requestParam) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData getCoupon(GetCouponRequest requestParam) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData getCouponList(GetCouponListRequest requestParam) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData updateCouponStatus(Long id, Integer status) {
        return getDownGradeResponse();
    }
}
