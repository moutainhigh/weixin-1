/*
 * (C) Copyright 2018 Siyue Holding Group.
 */
package cn.siyue.platform.weixin.client.callback.siyueli.member;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.weixin.client.callback.BaseServiceFallBack;
import cn.siyue.platform.weixin.client.service.siyueli.member.MemberBackendUserService;
import com.siyueli.platform.member.pojo.member.MemberUser;
import com.siyueli.platform.member.response.member.getuser.GetUserResponse;
import org.springframework.stereotype.Component;

/**
 * 会员后台熔断器
 */
@Component
public class MemberBackendUserServiceFallBack extends BaseServiceFallBack implements MemberBackendUserService {


  @Override
  public ResponseData<GetUserResponse> getUserByOpenId(String openId) {
    return getDownGradeResponse();
  }
}
