package cn.siyue.platform.weixin.client.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

public class PatternTest {

    @Test
    public void testPattern() {
        boolean result = Pattern.matches("(\\s*\\S*)*快餐店(\\s*\\S*)*", StringUtils.trimToEmpty(" bv快餐店aa"));
        Assert.assertTrue(result);
    }
}
