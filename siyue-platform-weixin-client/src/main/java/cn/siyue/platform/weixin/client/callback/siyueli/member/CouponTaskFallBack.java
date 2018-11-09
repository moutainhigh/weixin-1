package cn.siyue.platform.weixin.client.callback.siyueli.member;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.weixin.client.callback.BaseServiceFallBack;
import cn.siyue.platform.weixin.client.service.siyueli.member.CouponTaskClientService;
import com.siyueli.platform.member.common.PageResponse;
import com.siyueli.platform.member.pojo.coupon.CouponTask;
import com.siyueli.platform.member.request.coupon.GetCouponTaskListRequest;
import com.siyueli.platform.member.response.coupontask.GetCouponTaskListResponse;
import com.siyueli.platform.member.response.coupontask.GetCouponTaskResponse;
import org.springframework.stereotype.Component;

@Component
public class CouponTaskFallBack extends BaseServiceFallBack implements CouponTaskClientService {
    @Override
    public ResponseData<PageResponse<GetCouponTaskListResponse>> getCouponTaskList(GetCouponTaskListRequest requestParam) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData<GetCouponTaskResponse> getCouponTask(Long id) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData<GetCouponTaskResponse> getCouponTaskByCardId(String cardId) {
        return getDownGradeResponse();
    }
}
