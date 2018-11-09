package cn.siyue.platform.weixin.common.dto.weixin.card.request.settestwhitelist;

import lombok.Data;

import java.util.List;

@Data
public class SetTestWhiteListReq {

    private List<String> openid;

    private List<String> username;

}
