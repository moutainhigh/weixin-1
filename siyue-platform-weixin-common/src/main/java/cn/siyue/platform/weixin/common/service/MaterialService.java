package cn.siyue.platform.weixin.common.service;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.weixin.common.request.material.GetMaterialListRequest;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;

public interface MaterialService {

    public ResponseData getMaterialList(GetMaterialListRequest requestParam);

    public void deleteMaterial(String mediaId);

    public WxMpMaterialNews getNews(String mediaId);

}
