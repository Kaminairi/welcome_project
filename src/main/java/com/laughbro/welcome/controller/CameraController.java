package com.laughbro.welcome.controller;

import com.laughbro.welcome.dao.pojo.Camera;
import com.laughbro.welcome.utils.FaceComUtils;
import com.laughbro.welcome.utils.TimeUtils;
import com.laughbro.welcome.utils.OSSUtils;
import com.laughbro.welcome.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class CameraController {
    @Autowired
    private OSSUtils OSSUtils;//用于下载与上传
    @Autowired
    private FaceComUtils faceComUtils;
    @Autowired
    private TimeUtils timeUtils;

    String face_filePath = "G:\\goodworkres\\facepic\\";
    String temp_filePath="G:\\goodworkres\\facepic\\temp\\";

    //登录池子
    private static Map<String, BigInteger> faceCache = new ConcurrentHashMap<>();

    //相机池子
    private static Map<String, Camera> cameraCache =new ConcurrentHashMap<>();

    @PostMapping("/camera/loadin")
    public Result camera_loadin(String userid,String pwd){
        //判断登录

        //获得临时的cameratoken
        String combinedInfo = userid + timeUtils.timeGetNow();
        String shortToken = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(combinedInfo.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            shortToken = sb.toString().substring(0, 12); // 取前12位作为较短的token
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //注入空壳的camera对象
        Camera camera=new Camera(userid);
        cameraCache.put(shortToken,camera);
        return Result.success(shortToken);//返回特殊值
    }


    @DeleteMapping("camera/leave")
    public Result camera_leave(String cameratoken){

        cameraCache.remove(cameratoken);
        return Result.success(null);
    }

    @PostMapping("/camera/bindingloc")
    public Result camera_binding_loc(BigInteger locid,String cameratoken){

        cameraCache.get(cameratoken).setLocid(locid);
        return Result.success(null);
    }



/**
    *绑定了任务，并且创建了任务的文件夹
 */
    @PostMapping("/camera/bindingtask")
    public Result camera_binding_task(@RequestParam("taskArray") String[] taskArray, String cameratoken){
        //temp模拟已经登陆导入
        Camera camera=new Camera("admin_1", new BigInteger(String.valueOf(1234)));
        cameraCache.put("camera1",camera);
        //

        // 将 String 数组转换为 List
        List<String> dataList = Arrays.asList(taskArray);
        //注入绑定任务相机
        cameraCache.get(cameratoken).setTasklist(dataList);
        System.out.println(timeUtils.timeGetNow()+"      "+"ABAB 14415 --- [               ]                                          : 【 "+cameratoken+" 】相机加入相机池");
        //创建任务文件夹
        // 遍历 dataList 创建对应的文件夹
        for (String data : dataList) {
            // 指定文件夹路径
            String folderPath = this.face_filePath + "taskid_" + data;
            // 创建 File 对象
            File folder = new File(folderPath);

            if (folder.exists()) {
                System.out.println("                                                                                                   : 文件夹已经存在！" + folderPath);
                //System.out.println("文件夹已经存在：" + folderPath);
            } else {
                // 创建文件夹
                boolean success = folder.mkdirs(); // 可以创建多级目录
                if (success) {
                    System.out.println("                                                                                                   : 文件夹创建成功！" + folderPath);
                    //System.out.println("文件夹创建成功：" + folderPath);
                } else {
                    System.out.println("                                                                                                   : 文件夹创建失败！" + folderPath);
                    //System.out.println("文件夹创建失败：" + folderPath);
                }
            }
        }


        return null;
    }


    /**
     *
     * 进入定位范围内，会下载对应的pkl
     * @throws Exception
     */
    //当人的定位进入这个任务范围，就把他塞入存储
    @PostMapping("faceIn")
    public Result faceIn(String userid, BigInteger taskid) throws Exception {
        //录入这个人物
        faceCache.put(userid, taskid);
        System.out.println(timeUtils.timeGetNow()+"      "+"ABAB 14415 --- [               ]                                          :【 "+userid+" 】用户加入任务【 "+taskid+" 】临时池");
        // 指定文件夹路径
        String folderPath = this.face_filePath + "taskid_" + taskid;
        // 创建 File 对象
        File folder = new File(folderPath);
        // 创建文件夹
        if (folder.exists()) {
            System.out.println("                                                                                                   : 文件夹已经存在！" + folderPath);
            //System.out.println("文件夹已经存在：" + folderPath);
        } else {
            // 创建文件夹
            boolean success = folder.mkdirs(); // 可以创建多级目录
            if (success) {
                System.out.println("                                                                                                   : 文件夹创建成功！" + folderPath);
                //System.out.println("文件夹创建成功：" + folderPath);
            } else {
                System.out.println("                                                                                                   : 文件夹创建失败！" + folderPath);
                //System.out.println("文件夹创建失败：" + folderPath);
            }
        }
        //导入照片
        OSSUtils.download_pkl(folderPath, userid);
        return Result.success(null);
    }

    //移除这个人物
    @DeleteMapping("/faceOut")
    public Result faceOut(String userid, BigInteger taskid) {

        faceCache.remove(userid);
        System.out.println(timeUtils.timeGetNow()+"      "+"ABAB 14415 --- [               ]                                          :【 "+userid+" 】用户离开任务【 "+taskid+" 】临时池");

        // 指定要删除的文件路径
        String folderPath = this.face_filePath + "taskid_" + taskid + "\\" + userid + "_feature.pkl";
        // 创建 File 对象
        File file = new File(folderPath);
        // 尝试删除文件

        if (file.exists()) {
            // 文件存在，尝试删除
            if (file.delete()) {
                System.out.println("                                                                                                   : 文件删除成功！" + folderPath);

            } else {
                System.out.println("                                                                                                   : 文件删除失败！" + folderPath);

            }
        } else {
            System.out.println("                                                                                                   : 文件不存在，删除失败！" + folderPath);

        }
        return Result.success(null);
    }

    @PostMapping("/uploadImage")
    public Result uploadImage(@RequestParam("file") MultipartFile file) {
        //判断是否为空
        if (file.isEmpty()) {
            return Result.fail(111,"上传的文件为空",null);
        }
        // 获取以及处理文件名
        String fileName = file.getOriginalFilename();//xxx_yyy.jpg
        int underscoreIndex = fileName.indexOf("_"); // 获取下划线的位置
        String xxxPart = fileName.substring(0, underscoreIndex); // 使用 substring 方法获取 xxx 部分
        // 指定文件保存目录
        String uploadDir = temp_filePath;;
        // 如果目录不存在，则创建目录
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            // 构建文件路径
            String filePath = uploadDir + fileName;
            File dest = new File(filePath);
            // 将文件保存到指定路径
            file.transferTo(dest);
            System.out.println(timeUtils.timeGetNow()+"      "+"ABAB 14415 --- [               ]                                          : 【 "+xxxPart+" 】任务相机端发送文件，保存路径为: " + filePath);
            //预处理
            String pyreturn=faceComUtils.Facecompare(filePath, "G:\\goodworkres\\facepic\\taskid_"+xxxPart);
            //解析答案//xxxxxx#xxxxxxxx#xxxxxxxx#xxxxxxx#@XXXXXXXXXX#xxxxxxxx获得@开头的
            String[] words = pyreturn.split("#"); // 使用 # 号分割字符串
            // 逐行打印数组中的元素
            for (String word : words) {
                System.out.println("                                                                                                   : py脚本的返回结果 :  # "+word);
            }
            for (String word : words) {
                if (word.startsWith("@")) { // 找出以 @ 开头的单词
                    return Result.success(word);
                }
            }
            //faceComUtils.Facecom();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
