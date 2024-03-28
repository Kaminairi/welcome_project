package com.laughbro.welcome.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laughbro.welcome.dao.pojo.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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


}
