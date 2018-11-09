package cn.siyue.platform.weixin.client.controller;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.httplog.annotation.LogAnnotation;
import cn.siyue.platform.weixin.common.request.material.GetMaterialListRequest;
import cn.siyue.platform.weixin.common.service.MaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "微信_素材接口")
@RestController
@RequestMapping("/material")
public class MaterialController extends BaseController {

    @Autowired
    private MaterialService materialService;

    @LogAnnotation
    @ApiOperation(nickname = "getMaterialList",value = "得到素材列表")
    @GetMapping("/getMaterialList")
    public ResponseData getMaterialList(@Valid GetMaterialListRequest requestParam, BindingResult result) {
        ResponseData errorResponse = getErrorResponse(result);
        if (errorResponse != null)
            return errorResponse;

        return materialService.getMaterialList(requestParam);
    }
}
