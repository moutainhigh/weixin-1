package cn.siyue.platform.weixin.client.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HttpDownloadUtil {

    private static final Integer CONNECT_SOCKET_TIME_OUT_LONG = 14000;
    private static final Integer CONNECT_TIME_OUT_LONG = 14000;

    public static byte[] download(String url) {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            client = HttpClients.createDefault();

            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(CONNECT_SOCKET_TIME_OUT_LONG)
                    .setConnectTimeout(CONNECT_TIME_OUT_LONG).build();
            response = null;

            HttpGet get = null;
            get = new HttpGet(url);

            get.setConfig(requestConfig);
            get.setHeader("Accept", "image/*");
            response = client.execute(get);

            HttpEntity entity = response.getEntity();
            InputStream inStream = entity.getContent();
            return readInputStream(inStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    private static byte[] readInputStream(InputStream inStream) {
        ByteArrayOutputStream outStream = null;
        try {
            outStream = new ByteArrayOutputStream();
            //创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while ((len = inStream.read(buffer)) != -1) {
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            //把outStream里的数据写入内存
            byte[] data = outStream.toByteArray();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭输入流
                if (inStream != null) {
                    inStream.close();
                }
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
