package cn.siyue.platform.weixin.client.controller;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.util.ResponseUtil;
import cn.siyue.platform.weixin.client.service.file.SiyueFilesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "微信_文件上传接口")
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private SiyueFilesService siyueFilesService;


    @ApiOperation(nickname = "upload",value = "上传文件接口")
    @PostMapping("/upload")
    public ResponseData upload(@RequestParam("file") MultipartFile multipartFile) {
        if(multipartFile.isEmpty()){
            return ResponseUtil.fail();
        }
        return siyueFilesService.handleFileUpload(multipartFile);
    }
}
