package com.laughbro.welcome.service.imp;

import com.laughbro.welcome.dao.mapper.PostMapper;
import com.laughbro.welcome.service.PostService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.post_params.PostDetailParams;
import com.laughbro.welcome.vo.params.post_params.PostForTaskParams;
import com.laughbro.welcome.vo.params.post_params.PostNormalParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImp implements PostService {
    @Autowired
    private PostMapper postMapper;
    @Override
    public Result GetPost(){
        return Result.success(postMapper.select_post_all());
    }
    @Override
    public void PostForTask(PostForTaskParams params){
        postMapper.insert_post_task(params);
    }
    @Override
    public void PostNormal(PostNormalParams params){
        postMapper.insert_post_normal(params);
    }
    @Override
    public Result PostDetail(PostDetailParams params){
        postMapper.update_post_clicktnum_by_id(params.getId());
        return Result.success(postMapper.select_post_by_id(params.getId()));
    }
}
