package cn.siyue.platform.weixin.client.test;

import cn.siyue.platform.weixin.client.WeixinClientApplication;
import cn.siyue.platform.weixin.client.util.CurrencyUtil;
import cn.siyue.platform.weixin.common.service.StoreService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.store.WxMpStoreBaseInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeixinClientApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StoreServiceTest {


    @Autowired
    private StoreService storeService;

    @Test
    public void testAddStore() {

        WxMpStoreBaseInfo request = WxMpStoreBaseInfo.builder().build();
        request.setSid("1");
        request.setBusinessName("斯越里");
        request.setBranchName("斯越里-广州");
        request.setProvince("广东省");
        request.setCity("广州市");
        request.setDistrict("海珠区");
        request.setAddress("环汇商业广场");
        request.setTelephone("020-88888888");
        String[] categories = {"体育", "购物"};
        request.setCategories(categories);
        request.setLongitude(CurrencyUtil.setHalfUp(new BigDecimal(115.32375)));
        request.setLatitude(CurrencyUtil.setHalfUp(new BigDecimal(25.097486)));
        try {
            storeService.getWxMpStoreService().add(request);
            System.out.println("poiId: " + request.getPoiId());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }
}
