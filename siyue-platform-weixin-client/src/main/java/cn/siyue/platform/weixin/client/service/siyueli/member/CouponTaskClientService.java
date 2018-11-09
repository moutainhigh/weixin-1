package cn.siyue.platform.weixin.client.service.siyueli.member;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.weixin.client.callback.siyueli.member.CouponTaskFallBack;
import com.siyueli.platform.member.common.PageResponse;
import com.siyueli.platform.member.request.coupon.GetCouponTaskListRequest;
import com.siyueli.platform.member.response.coupontask.GetCouponTaskListResponse;
import com.siyueli.platform.member.response.coupontask.GetCouponTaskResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "siyueli-member-service", fallback = CouponTaskFallBack.class)
public interface CouponTaskClientService {

    @RequestMapping(value = "/coupontask/getCouponTaskList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseData<PageResponse<GetCouponTaskListResponse>> getCouponTaskList(GetCouponTaskListRequest requestParam);

    @RequestMapping(value = "/coupontask/getCouponTask", method = RequestMethod.GET)
    ResponseData<GetCouponTaskResponse> getCouponTask(@RequestParam("id") Long id);

    @RequestMapping(value = "/coupontask/getCouponTaskByCardId", method = RequestMethod.GET)
    ResponseData<GetCouponTaskResponse> getCouponTaskByCardId(@RequestParam("cardId") String cardId);
}
