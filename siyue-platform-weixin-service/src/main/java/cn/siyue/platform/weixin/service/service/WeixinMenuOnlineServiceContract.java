package cn.siyue.platform.weixin.service.service;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.weixin.common.common.PageRequest;
import cn.siyue.platform.weixin.common.common.PageResponse;
import cn.siyue.platform.weixin.common.entity.WeixinMenu;
import cn.siyue.platform.weixin.common.entity.WeixinMenuOnline;
import cn.siyue.platform.weixin.common.response.weixinmenuonline.WeixinMenuOnlineVo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 微信菜单（与公众号同步） 服务类
 * </p>
 *
 * @author Sipin ERP Development Team
 */
public interface WeixinMenuOnlineServiceContract extends IService<WeixinMenuOnline> {

    public void add(WeixinMenu weixinMenu);

    public void deleteAll();

    public ResponseData<PageResponse<WeixinMenuOnlineVo>> getList(PageRequest requestParam);
}
