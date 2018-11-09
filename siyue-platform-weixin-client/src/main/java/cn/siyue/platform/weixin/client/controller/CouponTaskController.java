package cn.siyue.platform.weixin.client.controller;


import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.httplog.annotation.LogAnnotation;
import cn.siyue.platform.weixin.client.service.siyueli.member.CouponTaskClientService;
import com.siyueli.platform.member.common.PageResponse;
import com.siyueli.platform.member.request.coupon.GetCouponTaskListRequest;
import com.siyueli.platform.member.response.coupontask.GetCouponTaskListResponse;
import com.siyueli.platform.member.response.coupontask.GetCouponTaskResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "微信_卡券_卡券任务接口")
@RestController
@RequestMapping("/couponTask")
public class CouponTaskController {

    @Autowired
    private CouponTaskClientService couponTaskService;

    @ApiOperation(nickname = "getCouponTaskList", value = "得到卡券任务列表")
    @LogAnnotation
    @GetMapping("/getCouponTaskList")
    public ResponseData<PageResponse<GetCouponTaskListResponse>> getCouponTaskList(GetCouponTaskListRequest requestParam) {
        return couponTaskService.getCouponTaskList(requestParam);
    }

    @ApiOperation(nickname = "getCouponTask", value = "得到卡券任务")
    @LogAnnotation
    @GetMapping("/getCouponTask")
    public ResponseData<GetCouponTaskResponse> getCouponTask(Long id) {
        return couponTaskService.getCouponTask(id);
    }
}
