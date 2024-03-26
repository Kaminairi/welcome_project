package com.laughbro.welcome.controller;

import com.laughbro.welcome.dao.mapper.ManagerMapper;
import com.laughbro.welcome.dao.mapper.TaskMapper;
import com.laughbro.welcome.dao.mapper.UserMapper;
import com.laughbro.welcome.dao.pojo.Camera;
import com.laughbro.welcome.dao.pojo.Manager;
import com.laughbro.welcome.dao.pojo.User;
import com.laughbro.welcome.utils.FaceComUtils;
import com.laughbro.welcome.utils.JWTUtils;
import com.laughbro.welcome.utils.TimeUtils;
import com.laughbro.welcome.utils.OSSUtils;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.login_params.LoginIdpwdParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
    @Autowired
            private TaskMapper taskMapper;
    @Autowired
            private ManagerMapper managerMapper;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private HttpServletResponse response;
    private static String face_filePath = "G:\\goodworkres\\facepic\\";
    private static String temp_filePath="G:\\goodworkres\\facepic\\temp\\";

    //初始密码
    private static String INIT_PWD = "123456";

    //登录池子
    private static Map<String, BigInteger> faceCache = new ConcurrentHashMap<>();

    //相机池子
    private static Map<String, Camera> cameraCache =new ConcurrentHashMap<>();

    //根据管理员的账号获得其拥有权限的任务集合
    @GetMapping("camera/getset")
    public Result camera_getset(String adminid){




        return Result.success(null);
    }




    //根据任务集合获得在执行期间且type为人脸识别的任务
    @GetMapping("camera/gettask")
    public Result camera_gettask(String setid){




        return Result.success(null);
    }



    /**
     * 【调用接口】
     * 【作用】 通过管理员来进行相机注册
     *
     * @return 相机token
     */
    @PostMapping("/camera/loadin")
    public Result camera_loadin(@RequestBody LoginIdpwdParams loginIdpwdParams) {
        String id=loginIdpwdParams.getId();
        String pwd=loginIdpwdParams.getPwd();
        //判断登录
        //User user = userMapper.select_user_all_by_id(id);
        Manager manager = managerMapper.select_manager_all_by_id(id);
        //没有此用户
        if (manager == null) {
            return Result.fail(101, "登录请求失败：没有当前管理员", null);
        } else {
            //第一次登录,没有加密
            if (manager.getPwd().equals(INIT_PWD)) {
                //判断一下是不是输入的初始密码
                if (pwd.equals(manager.getPwd())) {
                    //生成加密
                    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    String encodedPassword = passwordEncoder.encode("123456");
                    //写入数据库
                    //userMapper.update_user_pwd_by_id(encodedPassword, user.getId());
                    managerMapper.update_manager_pwd_by_id(encodedPassword, manager.getId());
                    //return Result.success(encodedPassword);

                    //----------形成token--------------------------------------------------------------------
                    String token = jwtUtils.buildToken(manager.getId(), manager.getName());
                    //塞入head
                    response.addHeader("Authorization", token);


                    //return Result.success(manager);
                } else {
                    //密码错误
                    return Result.fail(101, "初始密码错误", null);
                }
            } else {
                //不是第一次登录
                //给输入值加密
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String rawPassword = pwd;
                String encodedPasswordFromDb = manager.getPwd();
                if (passwordEncoder.matches(rawPassword, encodedPasswordFromDb)) {
                    // 密码验证通过
                    //生成token
                    String token = jwtUtils.buildToken(manager.getId(), manager.getName());
                    System.out.println(token);
                    //塞入head
                    response.addHeader("Authorization", token);

                    //webSocket.sendMessage(user.getId() +" 登录了");


                    //return Result.success(manager);
                    //return Result.success(token);
                } else {
                    // 密码验证失败
                    return Result.fail(101, "密码错误", null);
                }
            }


            //获得临时的cameratoken
            String combinedInfo = id + timeUtils.timeGetNow();
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
            Camera camera = new Camera(id);
            cameraCache.put(shortToken, camera);
            manager.setCameratoken(shortToken);
            //消除密码信息
            manager.setPwd(null);
            return Result.success(manager);//返回特殊值
        }
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
        String xxxPart = fileName.substring(0, underscoreIndex); // 使用 substring 方法获取 xxx 部分 为相机token
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
            long startTime = System.currentTimeMillis();
            System.out.println(timeUtils.timeGetNow()+"      "+"ABAB 14415 --- [               ]                                          : 【 "+xxxPart+" 】任务相机端发送文件，保存路径为: " + filePath);
            //比较
            List<String> tasklist=cameraCache.get(xxxPart).getTasklist();
            List<String> resultlist = new ArrayList<>();
            for (String task : tasklist) {
                System.out.println("                                                                                                   : 查询任务相关 【 "+task+" 】");
                String pyreturn = faceComUtils.Facecompare(filePath, "G:\\goodworkres\\facepic\\taskid_" + task);
                // 这里可以对pyreturn进行处理，比如打印或者其他操作
                //System.out.println("pyreturn: " + pyreturn);
                //解析答案//xxxxxx#xxxxxxxx#xxxxxxxx#xxxxxxx#@XXXXXXXXXX#xxxxxxxx获得@开头的
                String[] words = pyreturn.split("#"); // 使用 # 号分割字符串
                // 逐行打印数组中的元素
                for (String word : words) {
                    System.out.println("                                                                                                   : py脚本的返回结果 :  # "+word);
                }
                System.out.println(" ");
                for (String word : words) {
                    if (word.startsWith("@")) { // 找出以 @ 开头的单词
                        resultlist.add(word);
                        //return Result.success(word);
                    }
                 }

            //String pyreturn=faceComUtils.Facecompare(filePath, "G:\\goodworkres\\facepic\\taskid_"+xxxPart);
            }
            // 记录结束时间
            long endTime = System.currentTimeMillis();

            // 计算时间差
            long duration = endTime - startTime;
            System.out.println(timeUtils.timeGetNow()+"      "+"ABAB 14415 --- [               ]                                          : 比对人脸耗时【 "+duration+"ms 】");
            //比较
            return Result.success(resultlist);
            //faceComUtils.Facecom();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //return null;
    }
}
