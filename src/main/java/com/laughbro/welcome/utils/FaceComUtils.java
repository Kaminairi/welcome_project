package com.laughbro.welcome.utils;

import org.junit.Test;
import org.python.core.Py;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;



//G:/mycodetest/opencv/data/jm/1.lena.jpg
//G:/mycodetest/opencv/facecom.py
//D:/Anaconda3/envs/opencv-py38
@Component
public class FaceComUtils {
@Test
    public void Facecom(){

        //String image_path = "G:/mycodetest/opencv/data/jm/1.lena.jpg";  // 设置图片路径
        String image_path = "G:/mycodetest/opencv/data/jm/2.cui.jpg";  // 设置图片路径
        String pythonScriptPath = "G:/mycodetest/opencv/face444.py";  // 设置 Python 脚本路径

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("D:/Anaconda3/envs/opencv-py38/python", pythonScriptPath, image_path);
            Process process = processBuilder.start();

            // 读取 Python 脚本的输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("Python 脚本执行完毧，退出码为: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    //G:\mycodetest\opencv\trainer\success\compare.py
    public String Facecompare(String input,String files){

        //String image_path = "G:/mycodetest/opencv/data/jm/1.lena.jpg";  // 设置图片路径
        //String image_path = "G:/mycodetest/opencv/data/jm/2.cui.jpg";  // 设置图片路径
        String pythonScriptPath = "G:\\mycodetest\\opencv\\trainer\\success\\compare.py";  // 设置 Python 脚本路径

        try {
            System.out.println("                                                                                                   : 调用py脚本 : "+pythonScriptPath);
            ProcessBuilder processBuilder = new ProcessBuilder("D:/Anaconda3/envs/opencv-py38/python", pythonScriptPath, input,files);
            Process process = processBuilder.start();

            // 读取 Python 脚本的输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            String resultline = null;//xxxxxx#xxxxxxxx#xxxxxxxx#xxxxxxx#@XXXXXXXXXX#xxxxxxxx
            while ((line = reader.readLine()) != null) {
                resultline=resultline+"#"+line;
                //System.out.println(line);
                //return line;
            }
            int exitCode = process.waitFor();
            System.out.println("                                                                                                   : Python 脚本执行完毕，退出码为: " + exitCode);
            return resultline;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
@Test
public void faceCompare() {
    String pythonScriptPath = "G:\\mycodetest\\opencv\\trainer\\success\\compare.py";  // 设置 Python 脚本路径

    try {
        ProcessBuilder processBuilder = new ProcessBuilder("D:/Anaconda3/envs/opencv-py38/python", pythonScriptPath, "G:\\goodworkres\\facepic\\temp\\5.cui.jpg", "G:\\goodworkres\\facepic\\taskid_22");
        processBuilder.redirectErrorStream(true); // 将错误流合并到输出流
        Process process = processBuilder.start();

        // 读取 Python 脚本的输出（包括错误信息）
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        int exitCode = process.waitFor();
        System.out.println("Python 脚本执行完毧，退出码为: " + exitCode);
    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
    }
}

    public void oneface_trans(String input_filepath,String output_filepath){
        try {
            String pythonScriptPath = "G:\\mycodetest\\opencv\\trainer\\success\\onepic_trans.py";  // 设置 Python 脚本路径
            ProcessBuilder processBuilder = new ProcessBuilder("D:/Anaconda3/envs/opencv-py38/python",pythonScriptPath,input_filepath,output_filepath);
            Process process = processBuilder.start();

            // 读取 Python 脚本的输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("Python 脚本执行完毧，退出码为: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
