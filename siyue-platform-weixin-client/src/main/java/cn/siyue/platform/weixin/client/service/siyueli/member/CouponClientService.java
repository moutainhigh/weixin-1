package cn.siyue.platform.weixin.client.service.siyueli.member;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.weixin.client.callback.siyueli.member.CouponFallBack;
import com.siyueli.platform.member.common.PageResponse;
import com.siyueli.platform.member.pojo.coupon.Coupon;
import com.siyueli.platform.member.request.coupon.AddCouponRequest;
import com.siyueli.platform.member.request.coupon.GetCouponListRequest;
import com.siyueli.platform.member.request.coupon.GetCouponRequest;
import com.siyueli.platform.member.request.coupon.UpdateCouponRequest;
import com.siyueli.platform.member.response.coupon.CouponResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "siyueli-member-service", fallback = CouponFallBack.class)
public interface CouponClientService {

    @RequestMapping(value = "/backend/coupon/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseData add(@RequestBody AddCouponRequest requestParam);

    @RequestMapping(value = "/backend/coupon/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseData update(@RequestBody UpdateCouponRequest requestParam);

    @RequestMapping(value = "/backend/coupon/getCoupon", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseData<CouponResponse> getCoupon(@RequestBody GetCouponRequest requestParam);

    @RequestMapping(value = "/backend/coupon/getCouponList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseData<PageResponse<CouponResponse>> getCouponList(@RequestBody GetCouponListRequest requestParam);

    @RequestMapping(value = "/backend/coupon/updateCouponStatus", method = RequestMethod.POST)
    ResponseData updateCouponStatus(@RequestParam("id") Long id, @RequestParam("status") Integer status);
}
