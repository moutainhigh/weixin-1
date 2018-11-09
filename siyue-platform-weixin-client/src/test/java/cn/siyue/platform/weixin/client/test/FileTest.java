package cn.siyue.platform.weixin.client.test;

import org.junit.Test;
import sun.security.action.GetPropertyAction;

import java.io.File;
import java.security.AccessController;

public class FileTest {

    @Test
    public void testTempDir() {
        File tmpdir = new File(AccessController
                .doPrivileged(new GetPropertyAction("java.io.tmpdir")));

        System.out.println(File.separator);
    }
}
