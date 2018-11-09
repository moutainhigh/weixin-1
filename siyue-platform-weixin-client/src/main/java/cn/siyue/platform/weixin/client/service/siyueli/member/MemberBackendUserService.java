/*
 * (C) Copyright 2018 Siyue Holding Group.
 */
package cn.siyue.platform.weixin.client.service.siyueli.member;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.weixin.client.callback.siyueli.member.MemberBackendUserServiceFallBack;
import com.siyueli.platform.member.pojo.member.MemberUser;
import com.siyueli.platform.member.response.member.getuser.GetUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 调用会员后台管理服务生产者的接口
 */
@FeignClient(name = "siyueli-member-service", path = "/member/backend/user", fallback = MemberBackendUserServiceFallBack.class)
public interface MemberBackendUserService {

  @RequestMapping(value = "/getUserByOpenId", method = RequestMethod.GET)
  ResponseData<GetUserResponse> getUserByOpenId(@RequestParam("openId") String openId);

}
