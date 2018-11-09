package cn.siyue.platform.weixin.client.callback.file;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.weixin.client.callback.BaseServiceFallBack;
import cn.siyue.platform.weixin.client.service.file.SiyueFilesService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class SiyueFilesFallBack extends BaseServiceFallBack implements SiyueFilesService {
    @Override
    public ResponseData handleFileUpload(MultipartFile file) {
        return getDownGradeResponse();
    }
}
