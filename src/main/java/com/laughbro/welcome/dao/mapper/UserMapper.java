package com.laughbro.welcome.dao.mapper;

import com.laughbro.welcome.dao.pojo.User;
import com.laughbro.welcome.vo.params.me_params.MeChangeImgParams;
import com.laughbro.welcome.vo.params.me_params.MeChangeNameParams;
import com.laughbro.welcome.vo.params.user_params.UserEditParams;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper

public interface UserMapper {
    /**
     * 【调用接口】 /login_idpwd
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
     * 【调用接口】 /login_idpwd
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
     * 【调用接口】 /login_sms
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
     * 【调用接口】 /me/change/name
     * 【作用】 更改用户昵称
     */
    @Update("update users set name=#{MeChangeNameParams.name} where id=#{MeChangeNameParams.userid}")
    void update_user_name_by_id(@Param("MeChangeNameParams")MeChangeNameParams params);
    /**
     * 【调用接口】 /me/change/img
     * 【作用】 更改用户密码
     */
    @Update("update users set img=#{MeChangeImg.img} where id=#{MeChangeImg.userid}")
    void update_user_img_by_id(@Param("MeChangeImg") MeChangeImgParams params);
    /**
     * 【调用接口】 /admin/add/users
     * 【作用】 excel批量导入用户
     */
    @Insert({
            "insert into users (id, class_id, name, pwd, sex, idcard, native_place, student_origin, birthday, tel, email, realname, img)",
            "values ",
            "(#{User.id}, #{User.classId}, #{User.name}, #{User.pwd}, #{User.sex}, #{User.idcard}, #{User.nativePlace}, #{User.studentOrigin}, #{User.birthday}, #{User.tel}, #{User.email}, #{User.realname}, #{User.img})",
    })
    Integer insert_batch_by_excel(@Param("User") User user);
    /**
     * 【调用接口】 /admin/delete/user
     * 【作用】 删除用户
     */
    @Delete("delete from users where id=#{userid}")
    int deldete_user_by_id(String userid);
    /**
     * 【调用接口】 /admin/edit/user
     * 【作用】 修改用户
     */
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
    /**
     * 【调用接口】 /admin/get/userall
     * 【作用】 获取全部用户
     */
    @Select("<script>"
            + "select * from users,school_relate "
            + "where users.class_id = school_relate.class_id "
            + "order by id "
            + "<if test='order != null'>"
            + " ${order}"
            + "</if>"
            + "</script>")
    List<User> select_user_all(@Param("order")String order);



    @Update("UPDATE `users` SET `admissionletterimg`= #{letterimg} WHERE `id`=#{userid}  and idcard=#{idcard}")
    public int update_user_letter(String letterimg,String userid,String idcard);
}
