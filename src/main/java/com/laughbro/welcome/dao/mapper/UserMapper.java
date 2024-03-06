package com.laughbro.welcome.dao.mapper;

import com.laughbro.welcome.dao.pojo.Task;
import com.laughbro.welcome.dao.pojo.User;
import com.laughbro.welcome.vo.params.me_params.MeChangeImgParams;
import com.laughbro.welcome.vo.params.me_params.MeChangeNameParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper

public interface UserMapper {
    /**
     * 【调用接口】 login_idpwd
     * 【作用】 通过id拉取用户信息
     * @param id 用户id
     * @return 存在返回 该用户所有信息
     *         不存在返回  null
     */
    @Select("select * " +
            "from users " +
            "where id=#{id}")
    public User select_user_all_by_id(String id);


    /**
     * 【调用接口】 login_idpwd
     * 【作用】 更新密码，用于更改密码和写入加密密码
     * @param pwd
     * @param id
     * @return
     */
    @Update("update users " +
            "set pwd=#{pwd} " +
            "where id like #{id} ")
    public void update_user_pwd_by_id(String pwd, String id);

    /**
     * 【调用接口】 login_sms
     * 【作用】 通过id拉取用户信息
     * @param id 用户id
     * @return 存在返回 该用户所有信息
     *         不存在返回  null
     */
    @Select("select * " +
            "from users " +
            "where tel=#{tel}")
    public User select_user_all_by_tel(String id);

    @Update("update users set name=#{MeChangeNameParams.name} where id=#{MeChangeNameParams.userid}")
    void update_user_name_by_id(@Param("MeChangeNameParams")MeChangeNameParams params);

    @Update("update users set img=#{MeChangeImg.img} where id=#{MeChangeImg.userid}")
    void update_user_img_by_id(@Param("MeChangeImg") MeChangeImgParams params);
}
