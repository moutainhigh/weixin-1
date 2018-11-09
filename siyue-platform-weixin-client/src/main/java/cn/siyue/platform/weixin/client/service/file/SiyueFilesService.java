/*
 * (C) Copyright 2018 Siyue Holding Group.
 */
package cn.siyue.platform.weixin.client.service.file;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.weixin.client.callback.file.SiyueFilesFallBack;
import cn.siyue.platform.weixin.client.config.FeignMultipartSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "files-service", path = "/siyue/files",configuration = FeignMultipartSupportConfig.class)
public interface SiyueFilesService {

  @PostMapping(value = "/fileUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  ResponseData handleFileUpload(@RequestPart(value = "file") MultipartFile file);


}
