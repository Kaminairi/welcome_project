package com.laughbro.welcome.service.imp;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.laughbro.welcome.dao.mapper.UserMapper;
import com.laughbro.welcome.dao.pojo.User;
import com.laughbro.welcome.service.UserService;
import com.laughbro.welcome.vo.PageResult;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.user_params.UserDeleteParams;
import com.laughbro.welcome.vo.params.user_params.UserEditParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Result AddUser(MultipartFile file) throws IOException {
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        // 将数据转化对象
        // 解析Excel文件
        List<User> users = reader.readAll(User.class);
        // 批量新增客户
        return Result.success("新增了"+userMapper.insert_batch_by_excel(users)+"个用户");
    }
    @Override
    public Result DeleteUser(UserDeleteParams params){
        if(userMapper.deldete_user_by_id(params.getUserid())>0){
            return Result.success("删除成功");
        }else{
            return Result.success("删除失败");
        }
    }

    @Override
    public Result GetUser(String userid){
        return userMapper.select_user_all_by_id(userid)!=null?Result.success(userMapper.select_user_all_by_id(userid)):Result.success("用户不存在");
    }
    @Override
    public Result EditUser(UserEditParams params){
        if(userMapper.update_user_by_id(params)>0){
            return Result.success("修改成功");
        }else{
            return Result.success("修改失败");
        }
    }
    @Override
    public PageResult GetUserAll(int page,int pagesize,int order){
        PageHelper.startPage(page,pagesize);
        Page<User> p=new Page<>();
        if(order==0){
            p=(Page<User>) userMapper.select_user_all("desc");
        }else{
            p=(Page<User>) userMapper.select_user_all("asc");
        }
        long totalpages=p.getTotal()/pagesize+1;
        return PageResult.success(p.getResult(),totalpages);
    }

}
