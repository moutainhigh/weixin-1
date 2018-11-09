package cn.siyue.platform.weixin.service.mapper;

import cn.siyue.platform.weixin.common.dto.weixincardcoupon.SearchCardCouponDto;
import cn.siyue.platform.weixin.common.entity.WeixinCardCoupon;
import cn.siyue.platform.weixin.common.request.weixincardcoupon.SearchCardCouponRequest;
import cn.siyue.platform.weixin.common.response.weixincardcoupon.SearchCardCouponResponse;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 微信卡券表 Mapper 接口
 * </p>
 *
 * @author Sipin ERP Development Team
 */
@Repository
public interface WeixinCardCouponMapper extends BaseMapper<WeixinCardCoupon> {

    List<SearchCardCouponDto> searchCoupon(@Param("queryParam") SearchCardCouponRequest queryParam, Pagination page);
}
