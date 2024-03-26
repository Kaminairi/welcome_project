package com.laughbro.welcome.dao.mapper;

import com.laughbro.welcome.dao.pojo.Task;
import com.laughbro.welcome.dao.pojo.User;
import com.laughbro.welcome.vo.params.me_params.MeChangeImgParams;
import com.laughbro.welcome.vo.params.me_params.MeChangeNameParams;
import com.laughbro.welcome.vo.params.user_params.UserEditParams;
import org.apache.ibatis.annotations.*;

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
    /**
     * 【调用接口】 me/change/name
     * 【作用】 更改用户昵称
     */
    @Update("update users set name=#{MeChangeNameParams.name} where id=#{MeChangeNameParams.userid}")
    void update_user_name_by_id(@Param("MeChangeNameParams")MeChangeNameParams params);
    /**
     * 【调用接口】 me/change/img
     * 【作用】 更改用户密码
     */
    @Update("update users set img=#{MeChangeImg.img} where id=#{MeChangeImg.userid}")
    void update_user_img_by_id(@Param("MeChangeImg") MeChangeImgParams params);

    @Insert({
            "<script>",
            "insert into users",
            "values ",
            "<foreach collection='users' item='user' index='index' separator=',' open='(' close=')' >",
            "#{user.*}",
            "</foreach>",
            "</script>"
    })
    Integer insert_batch_by_excel(List<User> users);

    @Delete("delete from users where id=#{userid}")
    int deldete_user_by_id(String userid);

    @Update("UPDATE users" +
            " SET name=#{UserEditParams.name}," +
            "    name=#{UserEditParams.name}," +
            "    pwd=#{UserEditParams.pwd}," +
            "    sex=#{UserEditParams.sex}," +
            "    idcard=#{UserEditParams.idcard}," +
            "    native_place=#{UserEditParams.nativePlace}," +
            "    student_origin=#{UserEditParams.studentOrigin}," +
            "    birthday=#{UserEditParams.birthday}," +
            "    tel=#{UserEditParams.tel}," +
            "    email=#{UserEditParams.email}," +
            "    realname=#{UserEditParams.realname}," +
            "    img=#{UserEditParams.img} " +
            "WHERE id=#{UserEditParams.id}\n")
    int update_user_by_id(@Param("UserEditParams") UserEditParams params);

    @Select("<script>"
            + "select * from users "
            + "order by id "
            + "<if test='order != null'>"
            + "${order}"
            + "</if>"
            + "</script>")
    List<User> select_user_all(@Param("order")String order);
}
