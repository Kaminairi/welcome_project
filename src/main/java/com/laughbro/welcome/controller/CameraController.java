package com.laughbro.welcome.controller;

import com.laughbro.welcome.dao.mapper.*;
import com.laughbro.welcome.dao.pojo.*;
import com.laughbro.welcome.service.OssService;
import com.laughbro.welcome.utils.FaceComUtils;
import com.laughbro.welcome.utils.JWTUtils;
import com.laughbro.welcome.utils.TimeUtils;
import com.laughbro.welcome.utils.OSSUtils;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.CameraFaceParams;
import com.laughbro.welcome.vo.params.CameraUploadParams;
import com.laughbro.welcome.vo.params.CameratasksParams;
import com.laughbro.welcome.vo.params.login_params.LoginIdpwdParams;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;



@RestController
public class CameraController {
    @Autowired
            private OSSUtils OSSUtils;  //用于下载与上传
    @Autowired
            private FaceComUtils faceComUtils;  //用于调用脚本
    @Autowired
            private TimeUtils timeUtils;  //用于获得最新的时间
    @Autowired
            private TaskMapper taskMapper;
    @Autowired
            private ManagerMapper managerMapper;
    @Autowired
            private LocationMapper locationMapper;
    @Autowired
    private CameraLogMapper cameraLogMapper;
    @Autowired
    private CameraRecordMapper cameraRecordMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
            private JWTUtils jwtUtils;  //token生成
    @Autowired
            private HttpServletResponse response;
    @Autowired
    private OSSUtils ossUtilsl;
    @Autowired
    private OssService ossService;
    private static String face_filePath = "D:\\goodworkres\\facepic\\";
    private static String temp_filePath="D:\\goodworkres\\facepic\\temp\\";

    //初始密码
    private static String INIT_PWD = "123456";

    //登录池子
    private static Map<String, BigInteger> faceCache = new ConcurrentHashMap<>();

    //相机池子
    private static Map<String, Camera> cameraCache =new ConcurrentHashMap<>();

    /**
     *
     */


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
            //注入locationlist
            List<Location> locationList= locationMapper.select_location_all();
            manager.setLocationList(locationList);
            return Result.success(manager);//返回特殊值
        }
    }
    /**
     *相机绑定地点
     */

    @PostMapping("/camera/bindingloc")
    public Result camera_binding_loc(BigInteger locid,String cameratoken){
        Camera camera=cameraCache.get(cameratoken);
        if(camera==null){
            return Result.fail(101,"不存在这个相机",null);
        }
        cameraCache.get(cameratoken).setLocid(locid);
        return Result.success(null);
    }


    //根据管理员的账号获得其拥有权限的任务集合
    @GetMapping("camera/getset")
    public Result camera_getset(String adminid,String locid){
        List<TaskSet> listset=taskMapper.get_set_by_managerid_locid_camera(adminid,locid);
        if(listset.isEmpty())
            return Result.fail(111,"没有记录",null);
        return Result.success(listset);
    }




    //根据任务集合获得在执行期间且type为人脸识别的任务
    @GetMapping("camera/gettask")
    public Result camera_gettask(BigInteger setid){
        List<Task> taskList =taskMapper.get_task_by_setid_camera(setid);
        if(taskList.isEmpty())
            return Result.fail(101,"没有查询结果",null);
        Iterator<Task> iterator = taskList.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (!"人脸打卡".equals(task.getType())) {
                iterator.remove(); // 删除类型不为“人脸识别”的任务
            }
        }

        return Result.success(taskList);
    }


    /**
     *绑定了任务，并且创建了任务的文件夹
     */
    @PostMapping("/camera/bindingtask")
    public Result camera_binding_task(@RequestBody CameratasksParams cameratasksParams){
        String taskArray =cameratasksParams.getTaskArray();
        String cameratoken =cameratasksParams.getCameratoken();
        Camera camera1=cameraCache.get(cameratoken);
        if(camera1==null){
            return Result.fail(101,"不存在这个相机",null);
        }
        //temp模拟已经登陆导入
        //Camera camera=new Camera("admin_1", new BigInteger(String.valueOf(1234)));
        //cameraCache.put("camera1",camera);
        //
        //解析输入值[x,x,x]
        // 将 String 数组转换为 List

        List<String> dataList =new ArrayList<>();
        dataList=parseCommaSeparatedValues(taskArray);
        if(dataList==null||dataList.isEmpty()){
            return Result.fail(201,"传入的任务集合错误",null);
        }

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
        //获得绑定后的结果，进行校验
        List<String> templist=cameraCache.get(cameratoken).getTasklist();
        if(areListsEqual(templist,dataList)){
            Camera camera2=cameraCache.get(cameratoken);
            cameraRecordMapper.insert_camrearecord(cameratoken,camera2.getManagerid(),taskArray,timeUtils.timeGetNow(),camera2.getLocid());
            return Result.success(templist);
        }else{
            //移除已经邦迪的内容
            cameraCache.get(cameratoken).setTasklist(null);
            return Result.fail(201,"注入失败",templist);

        }
    }



    /**
     *关闭相机，移除相机
     */
    @DeleteMapping("camera/delete")
    public Result camera_leave(String adminid, String cameratoken){
        Camera camera=cameraCache.get(cameratoken);
        if(camera==null){
            return Result.fail(101,"不存在这个相机",null);
        }
        if(camera.getManagerid().equals(adminid)){
            cameraCache.remove(cameratoken);
            return Result.success("移除成功");
        }else {
            return Result.fail(101,"存在相机但是你没有权限（不是你的相机",null);
        }


    }







    /**
     *
     * 进入定位范围内，会下载对应的pkl
     * @throws Exception
     */
    //当人的定位进入这个任务范围，就把他塞入存储
    @PostMapping("faceIn")
    public Result faceIn(@RequestBody CameraFaceParams cameraFaceParams) throws Exception {
        String userid=cameraFaceParams.getUserid();
        BigInteger taskid =cameraFaceParams.getTaskid();
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
    public Result faceOut(@RequestBody CameraFaceParams cameraFaceParams) {
        String userid=cameraFaceParams.getUserid();
        BigInteger taskid =cameraFaceParams.getTaskid();
        faceCache.remove(userid);

        // 指定要删除的文件路径
        String folderPath = this.face_filePath + "taskid_" + taskid + "\\" + userid + ".pkl";
        // 创建 File 对象
        File file = new File(folderPath);
        // 尝试删除文件

        if (file.exists()) {
            // 文件存在，尝试删除
            if (file.delete()) {
                System.out.println("                                                                                                   : 文件删除成功！" + folderPath);
                System.out.println(timeUtils.timeGetNow()+"      "+"ABAB 14415 --- [               ]                                          :【 "+userid+" 】用户离开任务【 "+taskid+" 】临时池");

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
        Camera camera=cameraCache.get(xxxPart);
        if(camera==null){
            return Result.fail(101,"不存在这个相机",null);
        }
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
            //获得相文件夹
            List<String> tasklist=cameraCache.get(xxxPart).getTasklist();
            //优化流程
            Iterator<String> iterator = tasklist.iterator();
            while (iterator.hasNext()) {
                String taskid = iterator.next();
                Path path = Paths.get("G:\\goodworkres\\facepic\\taskid_" + taskid);
                if (!Files.list(path).findAny().isPresent()) {
                    iterator.remove(); // 删除没有文件的文件夹
                }
            }
            //
            List<String> resultlist = new ArrayList<>();
            for (String task : tasklist) {
                System.out.println("                                                                                                   : 查询任务相关 【 "+task+" 】");
                //优化判断
                Path path = Paths.get("G:\\goodworkres\\facepic\\taskid_" + task);
                //如果文件夹内不存在用户就不用启动脚本
                if(Files.list(path).findAny().isPresent()){
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
                        String processedWord = word.substring(1); // 去掉单词中以 "@" 开头的字符
                        resultlist.add(processedWord);
                        //return Result.success(word);
                    }
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


    @PostMapping("/uploadImagenew")
    public Result uploadImagenew(@RequestParam("file") MultipartFile file) {
        //判断是否为空
        if (file.isEmpty()) {
            return Result.fail(111,"上传的文件为空",null);
        }
        // 获取以及处理文件名
        String fileName = file.getOriginalFilename();//xxx_yyy.jpg
        int underscoreIndex = fileName.indexOf("_"); // 获取下划线的位置
        String xxxPart = fileName.substring(0, underscoreIndex); // 使用 substring 方法获取 xxx 部分 为相机token
        Camera camera=cameraCache.get(xxxPart);
        if(camera==null){
            return Result.fail(101,"不存在这个相机",null);
        }
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
            //获得相文件夹
            List<String> tasklist=cameraCache.get(xxxPart).getTasklist();
            List<String> taskfilelist=new ArrayList<>();
            //优化流程
            Iterator<String> iterator = tasklist.iterator();
            while (iterator.hasNext()) {
                String taskid = iterator.next();
                Path path = Paths.get("G:\\goodworkres\\facepic\\taskid_" + taskid);
                if (!Files.list(path).findAny().isPresent()) {
                    iterator.remove(); // 删除没有文件的文件夹
                }else {
                    taskfilelist.add("G:\\goodworkres\\facepic\\taskid_" + taskid);
                }
            }
            //
            List<String> resultlist = new ArrayList<>();

                //System.out.println("                                                                                                   : 查询任务相关 【 "+task+" 】");
                //优化判断
                //Path path = Paths.get("G:\\goodworkres\\facepic\\taskid_" + task);
                //如果文件夹内不存在用户就不用启动脚本

                    String pyreturn = faceComUtils.faceCompare(filePath, taskfilelist);
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
                            String processedWord = word.substring(1); // 去掉单词中以 "@" 开头的字符
                            resultlist.add(processedWord);
                            //return Result.success(word);
                        }
                    }

                //String pyreturn=faceComUtils.Facecompare(filePath, "G:\\goodworkres\\facepic\\taskid_"+xxxPart);

            // 记录结束时间
            long endTime = System.currentTimeMillis();

            // 计算时间差
            long duration = endTime - startTime;
            System.out.println(timeUtils.timeGetNow()+"      "+"ABAB 14415 --- [               ]                                          : 比对人脸耗时【 "+duration+"ms 】");
            //处理比较结果


            //上传人脸记录，修改数据库1.错判2没有结果


            //删除本地缓存图片














            //比较
            return Result.success(resultlist);
            //faceComUtils.Facecom();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //return null;
    }

    @PostMapping("/uploadImage2")
    public Result uploadImage(@RequestBody CameraUploadParams cameraUploadParams) {
        String base64Content = null;
        String fileName;

        //对base64的信息进行处理----------------------------------------------------------------------------------------------------------------------------------
        try {
            // 提取 base64 编码的图片数据
            String[] data = cameraUploadParams.getBase64Image().split(",");
            base64Content = data[1];
            base64Content = base64Content.replace(" ", "+").replace("\r", "").replace("\n", "").trim();
            if (base64Content.length() >= 2) {
                base64Content = base64Content.substring(0, base64Content.length() - 2);
            }
            // 解码成字节数组
            byte[] imageBytes = Base64.getDecoder().decode(base64Content);
            // 生成一个唯一的文件名
            fileName = cameraUploadParams.getCameratoken()+"_"+UUID.randomUUID().toString() + ".jpg";
            // 将图片数据写入到文件
            try (OutputStream stream = new FileOutputStream(temp_filePath + fileName)) {
                stream.write(imageBytes);
            }
        } catch (Exception e) {
            // 如果失败保存处理后待解码的字符串到文件
            saveBase64StringToFile(base64Content);
            return Result.fail(201,"Error uploading image: " + e.getMessage(),null);
        }

        // 获取以及处理文件---------------------------------------------------------------------------------------------------------------------------------------------
        String cameratoken=cameraUploadParams.getCameratoken();
        Camera camera=cameraCache.get(cameratoken);//获得对应的相机对象
        if(camera==null){
            return Result.fail(101,"不存在这个相机",null);
        }

        try {
            long startTime = System.currentTimeMillis();
            System.out.println(timeUtils.timeGetNow()+"      "+"ABAB 14415 --- [               ]                                          : 【 "+cameratoken+" 】任务相机端发送文件，保存路径为: " + temp_filePath + fileName);
            //获得相文件夹
            List<String> tasklist=cameraCache.get(cameratoken).getTasklist();
            List<String> taskfilelist=new ArrayList<>();
            Iterator<String> iterator = tasklist.iterator();
            while (iterator.hasNext()) {
                String taskid = iterator.next();
                taskfilelist.add("D:\\goodworkres\\facepic\\taskid_" + taskid);

            }
            //调用脚本得到结果------------------------------------------------------------------------------------------------------------------------------------------
            List<String> resultlist = new ArrayList<>();
            //调用脚本
            String pyreturn = faceComUtils.faceCompare(temp_filePath + fileName, taskfilelist);
            //处理返回结果
            String[] words = pyreturn.split("#"); // 使用 # 号分割字符串
            // 逐行打印数组中的元素
            for (String word : words) {
                System.out.println("                                                                                                   : py脚本的返回结果 :  # "+word);
            }
            System.out.println(" ");
            for (String word : words) {
                if (word.startsWith("@")) { // 找出以 @ 开头的单词
                    String processedWord = word.substring(1); // 去掉单词中以 "@" 开头的字符
                    resultlist.add(processedWord);
                    //return Result.success(word);
                }
            }

            // 记录结束时间
            long endTime = System.currentTimeMillis();

            // 计算时间差
            long duration = endTime - startTime;
            System.out.println(timeUtils.timeGetNow()+"      "+"ABAB 14415 --- [               ]                                          : 比对人脸耗时【 "+duration+"ms 】");
            //处理比较结果


            //上传人脸记录,保存数据库-------------------------------------------------------------------------------------------------------------------------------------------
            String upload_path="tasks/facecomparetasks/";
            /// 读取本地文件内容为字节数组
            byte[] fileContent = Files.readAllBytes(new File(temp_filePath + fileName).toPath());
            // 创建StandardMultipartFile对象
            MultipartFile multipartFile = new MockMultipartFile("file", fileName, "image/jpeg", fileContent);
            ossService.uploadfile(multipartFile,upload_path+cameraUploadParams.getCameratoken()+"/");
            if(ossService.isFileUploaded(upload_path+ cameraUploadParams.getCameratoken()+"/"+fileName)){
                //上传成功就删除本地
                delatetempfile(temp_filePath + fileName);
                //导入数据库,并且完成任务

                int size = Math.min(tasklist.size(), resultlist.size());
                for (int i = 0; i < size; i++) {
                    BigInteger taskid = new BigInteger(tasklist.get(i));
                    //删去空格
                    String nextResult = resultlist.get(i).replaceAll("\\s+", "");
                    //增加log记录
                    int addnum = cameraLogMapper.insert_cameralog_add(camera.getManagerid(), camera.getLocid(), taskid, upload_path + fileName, timeUtils.timeGetNow(), nextResult, 0);

                    if(!nextResult.equals("nofind")){
                        User user=userMapper.select_user_all_by_id(nextResult);
                        if(user!=null){
                            //判断是否已经存在完成记录
                            if(taskMapper.select_countfill_with_id(user.getId(),taskid)==0){
                                //注入完成记录表
                                taskMapper.insert_task_fulfillment_camera(nextResult,taskid,timeUtils.timeGetNow());
                                //修改log记录success为1
                                cameraLogMapper.update_cameralog_success(camera.getManagerid(), camera.getLocid(), taskid, upload_path + fileName, 1);
                            }else{
                                //已经存在记录
                                //return Result.fail(201,"已经存在记录，不可以重复完成",null);
                                System.out.println("                                                                                                   : 任务"+ taskid +"  用户"+user.getId()+"任务已经完成过，不可重复完成");
                            }

                        }else {

                            return Result.fail(201,"人脸识别结果无人，异常",null);
                        }
                    }else{
                        System.out.println("记录为onfind,没有注入");
                    }

                }
            }else{
                //上传失败则删除本地
                delatetempfile(temp_filePath + fileName);
                return Result.fail(201,"人脸识别oss传输失败，已经删除本地文件，请再次识别",null);
            }



            return Result.success(resultlist);
            //faceComUtils.Facecom();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //return null;






    }
    //报错处理
    private void saveBase64StringToFile(String base64Content) {
        String time = timeUtils.timeGetNow();
        try (PrintWriter writer = new PrintWriter(new FileWriter(time+"failbase64Content.txt"))) {
            writer.println(base64Content);
        } catch (Exception e) {
            System.out.println("Error saving base64 string to file: " + e.getMessage());
        }
    }
    /**
     *判断两个list是否相等
     */

    public static boolean areListsEqual(List<String> list1, List<String> list2) {
        // 检查列表长度是否相等
        if (list1.size() != list2.size()) {
            return false;
        }

        // 逐个比较列表中的元素
        for (int i = 0; i < list1.size(); i++) {
            if (!Objects.equals(list1.get(i), list2.get(i))) {
                return false;
            }
        }

        // 如果所有元素都相同，则返回true
        return true;
    }
    /**
     *解析绑定任务的接口的字符串[xx,x,x,x]转化为list<string>
     */

    public static List<String> parseCommaSeparatedValues(String input) {
        List<String> output = new ArrayList<>();

        if (input.startsWith("[") && input.endsWith("]")) {
            // 去除首尾的方括号后，按逗号分割字符串
            String[] parts = input.substring(1, input.length() - 1).split(",");

            Set<String> seen = new HashSet<>();

            for (String part : parts) {
                String trimmedPart = part.trim();

                if (!seen.contains(trimmedPart)) {
                    seen.add(trimmedPart);
                    output.add(trimmedPart);
                } else {
                    System.out.println("数字重复，返回null表示错误"); //
                    return null; // 数字重复，返回null表示错误
                }
            }

            // 判断生成的内容是否都是数字
            if (output.stream().allMatch(s -> s.matches("\\d+"))) {
                return output;
            } else {
                System.out.println("生成内容不全为数字，返回null表示错误"); //
                return null; // 生成内容不全为数字，返回null表示错误
            }
        } else {
            System.out.println("字符串未以方括号包围，返回null表示格式错误"); //
            return null; // 字符串未以方括号包围，返回null表示格式错误
        }
    }

    public static boolean delatetempfile(String filepath){
        File file = new File(filepath);
        // 检查文件是否存在
        if (file.exists()) {
            // 尝试删除文件
            if (file.delete()) {
                System.out.println("文件删除成功"+temp_filePath + filepath);
                return true;
            } else {
                System.out.println("无法删除文件");
                return false;
            }
        } else {
            System.out.println("文件不存在");
            return false;
        }
    }



}





