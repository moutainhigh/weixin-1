package cn.siyue.platform.weixin.service.service.impl;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.util.ResponseUtil;
import cn.siyue.platform.weixin.common.common.PageRequest;
import cn.siyue.platform.weixin.common.common.PageResponse;
import cn.siyue.platform.weixin.common.entity.WeixinMenu;
import cn.siyue.platform.weixin.common.entity.WeixinMenuOnline;
import cn.siyue.platform.weixin.common.response.weixinmenuonline.WeixinMenuOnlineVo;
import cn.siyue.platform.weixin.service.mapper.WeixinCardCouponMapper;
import cn.siyue.platform.weixin.service.mapper.WeixinMenuOnlineMapper;
import cn.siyue.platform.weixin.service.service.WeixinMenuOnlineServiceContract;
import cn.siyue.platform.weixin.service.service.WeixinMenuServiceContract;
import cn.siyue.platform.weixin.service.util.ConvertUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 微信菜单（与公众号同步） 服务实现类
 * </p>
 *
 * @author Sipin ERP Development Team
 */
@Primary
@Service
public class WeixinMenuOnlineService extends ServiceImpl<WeixinMenuOnlineMapper, WeixinMenuOnline> implements WeixinMenuOnlineServiceContract {

    @Override
    public void add(WeixinMenu weixinMenu) {
        WeixinMenuOnline entity = new WeixinMenuOnline();
        BeanUtils.copyProperties(weixinMenu, entity);
        entity.setId(null);

        insertAllColumn(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll() {
        delete(null);
    }

    @Override
    public ResponseData<PageResponse<WeixinMenuOnlineVo>> getList(PageRequest requestParam) {
        EntityWrapper<WeixinMenuOnline> wrapper = new EntityWrapper<WeixinMenuOnline>();
        Page<WeixinMenuOnline> page = new Page<WeixinMenuOnline>(requestParam.getPage(), requestParam.getSize());
        Page<WeixinMenuOnline> pageResult = selectPage(page, wrapper);
        try {
            PageResponse<WeixinMenuOnlineVo> pageResponse = ConvertUtil.getPageResponse(pageResult, WeixinMenuOnlineVo.class);
            return ResponseUtil.success(pageResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseUtil.fail();
    }


}
