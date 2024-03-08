package com.laughbro.welcome.utils;

import org.junit.Test;
import org.python.core.Py;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;



//G:/mycodetest/opencv/data/jm/1.lena.jpg
//G:/mycodetest/opencv/facecom.py
//D:/Anaconda3/envs/opencv-py38
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








    /*
    String image_path = "G:/mycodetest/opencv/data/jm/1.lena.jpg"; // 替换为你实际的图片路径

    // 设置禁止导入 site 模块
    Properties props = new Properties();
    props.setProperty("python.import.site", "false");

    // 创建 Python 解释器并初始化
    PythonInterpreter.initialize(System.getProperties(), props, new String[0]);

    // 创建 Python 解释器
    PythonInterpreter interpreter = new PythonInterpreter();

    // 加载 Python 模块
    interpreter.exec("import sys");
    interpreter.exec("import os"); // 导入 os 模块
    interpreter.exec("import cv2"); // 导入 OpenCV 模块
    interpreter.exec("sys.path.append('D:/Anaconda3/envs/opencv-py38')");  // 设置 Python 脚本所在的目录
    interpreter.exec("sys.path.append('D:/Anaconda3/envs/opencv-py38/Lib')");
    interpreter.exec("sys.path.append('D:/Anaconda3/envs/opencv-py38/Lib/site-packages')"); // 添加 OpenCV 模块路径



    // 打印 Python 错误信息
    interpreter.exec("import traceback");
    interpreter.exec("traceback.print_exc()");
    // 加载 Python 脚本
    interpreter.execfile("G:/mycodetest/opencv/facecom.py");

    // 获取 Python 函数对象
    PyObject pyFunction = interpreter.get("recognize_roommate");

    // 将 Java 字符串转换为 Python 字符串
    PyObject pyImage_path = Py.newString(image_path);

    // 调用 Python 函数
    PyObject result = ((PyFunction) pyFunction).__call__(pyImage_path);

    // 处理 Python 函数的返回值
    System.out.println("Python 函数的返回值: " + result);
    */

    }

}
