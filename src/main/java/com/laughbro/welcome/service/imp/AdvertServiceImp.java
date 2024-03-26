package com.laughbro.welcome.service.imp;

import com.laughbro.welcome.dao.mapper.AdvertMapper;
import com.laughbro.welcome.service.AdvertService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.advert_params.AddAdvertParams;
import com.laughbro.welcome.vo.params.advert_params.DeleteAdvertParams;
import com.laughbro.welcome.vo.params.advert_params.EditAdvertParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvertServiceImp implements AdvertService {
    @Autowired
    private AdvertMapper advertMapper;

    @Override
    public Result AddAdvert(AddAdvertParams params){
        return Result.success(advertMapper.insert_ad(params));
    }
    @Override
    public Result DeleteAdvert(DeleteAdvertParams params){
        return Result.success(advertMapper.delete_advert_by_id(params.getId()));
    }
    @Override
    public Result EditAdvert(EditAdvertParams params){
        return Result.success(advertMapper.edit_advert_by_id(params));
    }
    @Override
    public Result GetAdvertByKeyword(String keyword){
        return Result.success(advertMapper.select_advert_by_keyword(keyword));
    }
    @Override
    public Result GetAdvertAll(){
        return Result.success(advertMapper.select_advert_all());
    }
}
