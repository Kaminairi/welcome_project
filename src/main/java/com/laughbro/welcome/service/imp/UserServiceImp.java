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

import java.util.List;


@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserMapper userMapper;
    /**
     * 【作用】excel批量导入用户
     */
    @Override
    public Result AddUser(MultipartFile file) {
        try {
            ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
            List<User> users = reader.readAll(User.class);
            int count = 0;
            for(User user:users){
                if(userMapper.select_user_all_by_id(user.getId())==null){
                    userMapper.insert_batch_by_excel(user);
                    count++;
                }
            }
            return Result.success("新增了" + count + "个用户");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.success("数据插入失败");
        }
    }
    /**
     * 【作用】删除用户
     */
    @Override
    public Result DeleteUser(UserDeleteParams params){
        if(userMapper.deldete_user_by_id(params.getUserid())>0){
            return Result.success("删除成功");
        }else{
            return Result.success("删除失败");
        }
    }
    /**
     * 【作用】搜索用户
     */
    @Override
    public Result GetUser(String userid){
        return userMapper.select_user_all_by_id(userid)!=null?Result.success(userMapper.select_user_all_by_id(userid)):Result.success("用户不存在");
    }
    /**
     * 【作用】修改用户信息
     */
    @Override
    public Result EditUser(UserEditParams params){
        try {
            if(userMapper.update_user_by_id(params)>0){
                return Result.success("修改成功");
            }else{
                return Result.success("修改失败");
            }
        }catch (Exception e){
            return Result.fail(100,"defaut","修改失败");
        }
    }
    /**
     * 【作用】获取全部用户列表
     */
    @Override
    public PageResult GetUserAll(int page,int pagesize,int order){
        PageHelper.startPage(page,pagesize);
        Page<User> p;
        if(order==0){
            p=(Page<User>) userMapper.select_user_all("desc");
        }else{
            p=(Page<User>) userMapper.select_user_all("asc");
        }
        long totalpages=p.getTotal()/pagesize+1;
        return p.isEmpty()?PageResult.success("暂无用户",0):PageResult.success(p.getResult(),totalpages);
    }

}
