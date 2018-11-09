package cn.siyue.platform.weixin.common.response.material;

import lombok.Data;

import java.util.Date;

@Data
public class MaterialVo {

    private String mediaId;

    private String title;

    private Date updateTime;

    private String url;
}
