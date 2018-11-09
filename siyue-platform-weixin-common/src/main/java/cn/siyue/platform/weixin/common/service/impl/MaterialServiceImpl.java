package cn.siyue.platform.weixin.common.service.impl;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.util.ResponseUtil;
import cn.siyue.platform.weixin.common.common.PageResponse;
import cn.siyue.platform.weixin.common.request.material.GetMaterialListRequest;
import cn.siyue.platform.weixin.common.response.material.MaterialVo;
import cn.siyue.platform.weixin.common.service.MaterialService;
import cn.siyue.platform.weixin.common.service.WeixinMpService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMaterialService;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialFileBatchGetResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNewsBatchGetResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private WeixinMpService weixinMpService;

    @Override
    public ResponseData getMaterialList(GetMaterialListRequest requestParam) {
        if (!WxConsts.MaterialType.IMAGE.equals(requestParam.getType())
                && !WxConsts.MaterialType.NEWS.equals(requestParam.getType())
                && !WxConsts.MaterialType.VIDEO.equals(requestParam.getType())
                && !WxConsts.MaterialType.VOICE.equals(requestParam.getType())) {
            return ResponseUtil.fail("素材类型不正确");
        }
        try {
            PageResponse<MaterialVo> pageResponse = null;
            if (WxConsts.MaterialType.NEWS.equals(requestParam.getType())) {
                WxMpMaterialNewsBatchGetResult newsResult= getNewsMaterialList(requestParam);
                pageResponse = getNewsPageResponse(requestParam, newsResult);
            } else {
                WxMpMaterialFileBatchGetResult fileResult = getFileMaterialList(requestParam);
                pageResponse = getFilePageResponse(requestParam, fileResult);

            }
            return ResponseUtil.success(pageResponse);


        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return ResponseUtil.fail();
    }

    private PageResponse<MaterialVo> getNewsPageResponse(GetMaterialListRequest requestParam, WxMpMaterialNewsBatchGetResult result) {
        if (result != null) {
            PageResponse<MaterialVo> pageResponse = new PageResponse();
            pageResponse.setCurrent(requestParam.getPage());
            pageResponse.setSize(requestParam.getSize());
            pageResponse.setTotal(result.getTotalCount());
            int pages = pageResponse.getTotal() % pageResponse.getSize() == 0 ? pageResponse.getTotal() / pageResponse.getSize() : pageResponse.getTotal() / pageResponse.getSize() + 1;
            pageResponse.setPages(pages);

            List<WxMpMaterialNewsBatchGetResult.WxMaterialNewsBatchGetNewsItem> list = result.getItems();
            if (list != null && !list.isEmpty()) {
                List<MaterialVo> materialList = new ArrayList<MaterialVo>();
                pageResponse.setRecords(materialList);
                for (WxMpMaterialNewsBatchGetResult.WxMaterialNewsBatchGetNewsItem item : list) {
                    MaterialVo materialVo = new MaterialVo();
                    materialVo.setMediaId(item.getMediaId());
                    materialVo.setUpdateTime(item.getUpdateTime());
                    WxMpMaterialNews newsContent = item.getContent();
                    if (newsContent != null) {
                        List<WxMpMaterialNews.WxMpMaterialNewsArticle> articleList = newsContent.getArticles();
                        if (articleList != null && !articleList.isEmpty()) {
                            materialVo.setTitle(articleList.get(0).getTitle());
                            materialVo.setUrl(articleList.get(0).getUrl());
                        }

                    }

                    materialList.add(materialVo);
                }
            }

            return pageResponse;
        }
        return null;
    }


    private PageResponse<MaterialVo> getFilePageResponse(GetMaterialListRequest requestParam, WxMpMaterialFileBatchGetResult result) {
        if (result != null) {
            PageResponse<MaterialVo> pageResponse = new PageResponse();
            pageResponse.setCurrent(requestParam.getPage());
            pageResponse.setSize(requestParam.getSize());
            pageResponse.setTotal(result.getTotalCount());
            int pages = pageResponse.getTotal() % pageResponse.getSize() == 0 ? pageResponse.getTotal() / pageResponse.getSize() : pageResponse.getTotal() / pageResponse.getSize() + 1;
            pageResponse.setPages(pages);

            List<WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem> list = result.getItems();
            if (list != null && !list.isEmpty()) {
                List<MaterialVo> materialList = new ArrayList<MaterialVo>();
                pageResponse.setRecords(materialList);
                for (WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem item : list) {
                    MaterialVo materialVo = new MaterialVo();
                    materialVo.setMediaId(item.getMediaId());
                    materialVo.setUpdateTime(item.getUpdateTime());
                    materialVo.setTitle(item.getName());
                    materialVo.setUrl(item.getUrl());
                    materialList.add(materialVo);
                }
            }

            return pageResponse;
        }
        return null;
    }

    private WxMpMaterialNewsBatchGetResult getNewsMaterialList(GetMaterialListRequest requestParam) throws WxErrorException {
        WxMpMaterialService wxMpMaterialService = weixinMpService.getWxMpService().getMaterialService();
        int offset = (requestParam.getPage() - 1) * requestParam.getSize();
        WxMpMaterialNewsBatchGetResult result = wxMpMaterialService.materialNewsBatchGet(offset, requestParam.getSize());
        return result;
    }

    private WxMpMaterialFileBatchGetResult getFileMaterialList(GetMaterialListRequest requestParam) throws WxErrorException {
        WxMpMaterialService wxMpMaterialService = weixinMpService.getWxMpService().getMaterialService();
        int offset = (requestParam.getPage() - 1) * requestParam.getSize();
        WxMpMaterialFileBatchGetResult result = wxMpMaterialService.materialFileBatchGet(WxConsts.MaterialType.IMAGE, offset, requestParam.getSize());

        return result;
    }

    @Override
    public void deleteMaterial(String mediaId) {
        WxMpMaterialService wxMpMaterialService = weixinMpService.getWxMpService().getMaterialService();
        try {
            wxMpMaterialService.materialDelete(mediaId);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public WxMpMaterialNews getNews(String mediaId) {
        WxMpMaterialService wxMpMaterialService = weixinMpService.getWxMpService().getMaterialService();
        try {
            WxMpMaterialNews newsInfo = wxMpMaterialService.materialNewsInfo(mediaId);
            return newsInfo;
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    }
}
