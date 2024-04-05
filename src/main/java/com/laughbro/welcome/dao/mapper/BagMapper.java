package com.laughbro.welcome.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laughbro.welcome.dao.pojo.Item;
import com.laughbro.welcome.dao.pojo.ScoreSort;
import jnr.ffi.annotations.In;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface BagMapper {

    /**
     * 【调用接口】 login_idpwd
     * 【作用】 通过id拉取用户背包信息
     * @param id 用户id
     * @return
     */
    @Select("SELECT * " +
            "FROM item_possession ,items " +
            "WHERE item_possession.user_id LIKE #{id} " +
            "AND item_possession.item_id=items.id")
    //public List<Item> select_itemown_all_by_id(Page<Item> page, String id);
    public List<Item> select_itemown_all_by_id( String id);

    /**
     * 【调用接口】
     * 【作用】 获取物品
     * @param
     * @return
     */
    @Select("CALL sp_update_bag_items_by_userid_itemid_num(#{user_id},#{item_id},#{num});")
    public List<Item> update_itemown_by_userid_itemd_num(String user_id, BigInteger item_id,int num);

    @Select("select user_id,users.img,users.name,sum(own_num) as score from users,item_possession where item_id=(select id from items where name='积分') and user_id=users.id group by user_id order by score desc;")
    List<ScoreSort> select_score_all();

    @Insert("insert into items " +
            "(name,`desc`,creator,ctime,dtime,img) " +
            "values " +
            "(#{name},#{desc},#{creator},#{ctime},#{dtime},#{img})")
    public int insert_item(String name,String desc,String creator,String ctime,String dtime,String img);

    @Update("update items " +
            "set name =#{name}, " +
            "`desc` =#{desc}, " +
            "dtime=#{dtime}, " +
            "img=#{img} " +
            "where id=#{id}")
    public int update_item(String id,String name,String desc,String dtime,String img);

    @Select("SELECT img " +
            "from items " +
            "where id =#{id}")
    public String select_img_items_by_id(String id);


    @Select("call sp_update_bag_items_by_userid_itemid_num(#{userid},#{itemid},#{rewardNum})")
    void insert_itempossession(String userid, String itemid, String rewardNum);


    @Select("SELECT * from items ")
    List<Item> select_all_items();
}
