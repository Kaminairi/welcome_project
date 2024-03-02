package com.laughbro.welcome.dao.mapper;

import com.laughbro.welcome.dao.pojo.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
    public List<Item> select_itemown_all_by_id(String id);




}
