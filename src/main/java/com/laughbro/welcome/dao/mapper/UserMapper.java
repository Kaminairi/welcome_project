package com.laughbro.welcome.dao.mapper;

import com.laughbro.welcome.dao.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper

public interface UserMapper {
    /**
     * 【调用接口】 login
     * 【作用】 判断账号密码是否存在以及匹配
     * @param
     * @return 存在返回 该用户所有信息
     *         不存在返回  null
     */
    @Select("select * from users where id=#{id}")
    public User login_select_user_exist_by_id(String id);





}
