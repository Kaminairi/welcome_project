package com.laughbro.welcome.service.imp;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laughbro.welcome.dao.mapper.BagMapper;
import com.laughbro.welcome.dao.pojo.Item;
import com.laughbro.welcome.service.BagService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.bag_params.BagViewItemParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BagServiceImp implements BagService{

    @Autowired
    private BagMapper bagMapper;


    @Override
    public Result bag_viewall(BagViewItemParams bagViewItemParams){
        Page<Item> page = new Page<>(1, 4);
        List<Item> userbag = bagMapper.select_itemown_all_by_id(page,bagViewItemParams.getId());


        return Result.success(userbag);

    }




}
