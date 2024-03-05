package com.laughbro.welcome.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.junit.Test;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


@Component
public class PICUtils {


    public PICUtils() throws com.aliyuncs.exceptions.ClientException {
    }

    @Test
    public void downloadPic() throws com.aliyuncs.exceptions.ClientException, MalformedURLException, FileNotFoundException {
        String url = "https://goodworl.oss-cn-beijing.aliyuncs.com/items/brave.jpg?Expires=1709379552&OSSAccessKeyId=TMP.3Kjs1k3Jad8oq8NjGfG8UDHBQtgY5Lkjbj7QhQcsijauBoQ35d6Kn3BeAK93WGfKWUYecmKt4npdatFqMqLNwKT61zBahf&Signature=xY%2ByUHSISS8ZIpGix%2FlHdpLcBbw%3D";
        String pathName = "G:\\桌面\\welcome\\src\\main\\resources\\items\\brave.jpg";

        try {
            // 创建URL对象并打开连接
            URL fileUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) fileUrl.openConnection();

            // 获取输入流以读取文件内容
            InputStream inputStream = connection.getInputStream();

            // 创建输出流以写入文件
            FileOutputStream fileOutputStream = new FileOutputStream(pathName);

            // 通过缓冲区逐个字节地将文件内容写入本地文件
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            // 关闭流
            fileOutputStream.close();
            inputStream.close();

            System.out.println("文件下载完成");
        } catch (IOException e) {
            System.out.println("发生错误: " + e.getMessage());
        }
    }


}






