package com.laughbro.welcome.service.imp;

import com.laughbro.welcome.dao.mapper.AdvertMapper;
import com.laughbro.welcome.dao.pojo.Advert;
import com.laughbro.welcome.service.AdvertService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.advert_params.AddAdvertParams;
import com.laughbro.welcome.vo.params.advert_params.DeleteAdvertParams;
import com.laughbro.welcome.vo.params.advert_params.EditAdvertParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertServiceImp implements AdvertService {
    @Autowired
    private AdvertMapper advertMapper;
    /**
     * 【作用】 管理员发布广告
     */
    @Override
    public Result AddAdvert(AddAdvertParams params){
        try {
            if(advertMapper.insert_ad(params)==1){
                return Result.success("广告添加成功");
            }else{
                return Result.fail(100,"fail","广告添加失败");
            }
        }catch (Exception e){
            return Result.fail(100,"fail","广告添加失败");
        }

    }
    /**
     * 【作用】 管理员删除广告
     */
    @Override
    public Result DeleteAdvert(DeleteAdvertParams params){
        if(advertMapper.delete_advert_by_id(params.getId())==1) {
            return Result.success("删除成功");
        }else{
            return Result.fail(100,"fail","删除失败");
        }
    }
    /**
     * 【作用】 管理员修改广告
     */
    @Override
    public Result EditAdvert(EditAdvertParams params){
        try{
            if(advertMapper.update_advert_by_id(params)==1){
                return Result.success("广告修改成功");
            }else{
                return Result.fail(100,"fail","广告修改失败");
            }
        }catch (Exception e){
            return Result.fail(100,"fail","广告修改失败");
        }

    }
    /**
     * 【作用】 管理员搜索广告
     */
    @Override
    public Result GetAdvertByKeyword(String keyword){
        List<Advert> list=advertMapper.select_advert_by_keyword(keyword);
        if(list.isEmpty()){
            return Result.success("还没有相关广告");
        }else{
            return Result.success(list);
        }
    }
    /**
     * 【作用】 管理员查看全部广告
     */
    @Override
    public Result GetAdvertAll(){
        List<Advert> list=advertMapper.select_advert_all();
        if(list.isEmpty()){
            return Result.success("还没有广告");
        }else{
            return Result.success(list);
        }
    }
}
