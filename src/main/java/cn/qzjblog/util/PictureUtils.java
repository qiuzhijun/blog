package cn.qzjblog.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Create by qzj on 2021/01/22 15:33
 **/
public class PictureUtils {
    public static String isImagesTrue(String posturl) throws IOException {
        URL url = new URL(posturl);
        HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
        urlcon.setRequestMethod("POST");
        urlcon.setRequestProperty("Content-type",
                "application/x-www-form-urlencoded");
        if (urlcon.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return "200";
        } else {
            return "404";
        }
    }

    public static int testWsdlConnection(String address) {
        int status = 404;
        try {
            if (address.startsWith("http")) {
                URL urlObj = new URL(address);
                HttpURLConnection oc = (HttpURLConnection) urlObj.openConnection();
                oc.setUseCaches(false);
                oc.setConnectTimeout(3000); // 设置超时时间
                status = oc.getResponseCode();// 请求状态
                if (200 == status) {
                    // 200是请求地址顺利连通。。
                    return status;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }


}
