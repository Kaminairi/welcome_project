package com.laughbro.welcome.utils;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class urltest {

    @Test
    public  void download() throws Exception {

        String urlString="https://newgoodwork.oss-cn-hangzhou.aliyuncs.com/items/brave.jpg?Expires=1710057988&OSSAccessKeyId=STS.NUshUZES2Sdu68fysRMU4Pg3J&Signature=RhwxC0osoyHIxc2lInUmkLO91x4%3D&security-token=CAIS7wF1q6Ft5B2yfSjIr5bGI%2B%2FuqIwT5KaeNB7XnXMHQdoYv6KYqDz2IHhMeXJpCOgZsv0ym2pZ6P4elqVoRoReREvCWpIots0PUd8AsACY6aKP9rUhpMCPOwr6UmzWvqL7Z%2BH%2BU6muGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwMDL1VJH7aCwBLH9BLPABvhdYHPH%2FKT5aXPwXtn3DbATgD2GM%2BqxsmtfznnJXNsEKP0g2nlLNOnemrfMj4NfsLFYxkTtK40NZxcqf8yyNK43BIjvwu1PEfoGaf4Y3EXgYLskveb%2FC48dRqLR%2Bao2ah%2BH2SxhqAAaxX9xEbrjsWZUTh3i70bS7g%2BRUL32LjjhgvYaFyAlAedhmiKwC5kBsiRnZjOy9wvNYvYXJkF1Cy1nU3e3yFm6HjTD9NKxz%2B%2F7LfbrwybyUXK9RzhCkt7zMYQf%2BRMye3keNxJAX0IryX4M4BQAEnqrTG9p0tf4OTOZYlJ%2FZS8wTrIAA%3D"
                ;
        URL url = new URL(urlString);

        // 打开连接

        URLConnection con = url.openConnection();

        // 输入流

        InputStream is = con.getInputStream();

        // 1K的数据缓冲

        byte[] bs = new byte[1024];

        // 读取到的数据长度

        int len;

        // 输出的文件流

        String filename = "G:\\桌面\\welcome\\src\\main\\resources\\items/brave.jpg";  //下载路径及下载图片名称

        File file = new File(filename);

        FileOutputStream os = new FileOutputStream(file, true);

        // 开始读取

        while ((len = is.read(bs)) != -1) {

            os.write(bs, 0, len);

        }



        // 完毕，关闭所有链接

        os.close();

        is.close();

    }
}
