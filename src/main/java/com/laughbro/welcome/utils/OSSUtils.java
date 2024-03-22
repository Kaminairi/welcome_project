package com.laughbro.welcome.utils;

import org.junit.Test;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
@Component
public class OSSUtils {

    @Test
    public  void download_pkl() throws Exception {

        String urlString="https://newgoodwork.oss-cn-hangzhou.aliyuncs.com/users/faceimg/faceimg_1111111111.jpg?Expires=1710152295&OSSAccessKeyId=STS.NTuo1fXTPCpdTYSHYpCvCwk1D&Signature=Ws4CHyK6rgCG7gzhjn0A7Bgg8P0%3D&security-token=CAIS7wF1q6Ft5B2yfSjIr5fAJIvStYtx9LKPVn%2FirFklT%2FlvmK6apjz2IHhMeXJpCOgZsv0ym2pZ6P4elqVoRoReREvCWpIots0POKgM4wCY6aKP9rUhpMCPOwr6UmzWvqL7Z%2BH%2BU6muGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwMDL1VJH7aCwBLH9BLPABvhdYHPH%2FKT5aXPwXtn3DbATgD2GM%2BqxsmtfznnJXNsEKP0g2nlLNOnemrfMj4NfsLFYxkTtK40NZxcqf8yyNK43BIjvwu1PEfoGaf4Y3EXgYLskveb%2FC48dRqLR%2Bao2ah%2BH2SxhqAAbIpg%2FPFIjTdyMNAZkJRX1PlRXQJdPAgOVZoPqNUL8WuppXJ84mGeT2HFKNV7jDExcheGg5cOvDLdb1dKIp684MMdHjXrPWrqgPKvsmqSAM4%2FRiHHbvfLYvHs1RtvePwgLfgXXqTk7yUaDtVMZtRtezcRMAoVhk6XhPWr33DYTCcIAA%3D"
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
        String filename = "G:\\桌面\\welcome\\src\\main\\resources\\items/1.jpg";  //下载路径及下载图片名称
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

    public  void download_pkl(String filepath, String userid) throws Exception {
        //newgoodwork/users/facecompare/facepkl/
        String urlString="https://newgoodwork.oss-cn-hangzhou.aliyuncs.com/users/facecompare/facepkl/"+userid+"_feature.pkl"
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
        String filename = filepath+"/"+userid+"_feature.pkl";  //下载路径及下载图片名称
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

    //public void upload(File upfile,String uptype)

}
