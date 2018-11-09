package cn.siyue.platform.weixin.client.test;

import cn.siyue.platform.weixin.client.WeixinClientApplication;
import cn.siyue.platform.weixin.client.controller.CardCouponController;
import cn.siyue.platform.weixin.client.util.SpringUtil;
import cn.siyue.platform.weixin.common.config.WeixinConfig;
import cn.siyue.platform.weixin.common.service.MaterialService;
import me.chanjar.weixin.common.util.crypto.SHA1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeixinClientApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class KeywordReplyServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeywordReplyServiceTest.class);

    @Autowired
    private MaterialService materialService;

    @Autowired
    private WeixinConfig weixinConfig;

    @Autowired
    private SpringUtil springUtil;

    /*@Test
    public void testDeleteMaterial() {
        String mediaId = "mQPEDpb1UFnQgRLlV6iLdC9v-VCksC--fcchQEmRPbk";
        materialService.deleteMaterial(mediaId);
    }*/

    @Test
    public void testSign() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String nonce = "135231";
        String sign = SHA1.gen(weixinConfig.getToken(), timestamp, nonce);

        System.out.println("timestamp: " + timestamp + ", nonce: " + nonce + ", sign: " + sign);
    }

    @Test
    public void testApplicationContext() {
        Map<String, CardCouponController> map = springUtil.getApplicationContext().getBeansOfType(CardCouponController.class);
    }

    @Test
    public void testLogBack() {
        if (1 == 1) {
            try {
                throw new Exception("测试异常");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}
