package com.laughbro.welcome.service.imp;

import com.laughbro.welcome.dao.mapper.PostMapper;
import com.laughbro.welcome.service.PostService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.post_params.PostForTaskParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImp implements PostService {
    @Autowired
    private PostMapper postMapper;
    @Override
    public void PostForTask(PostForTaskParams params){
        postMapper.PostForTask(params);
    }
}
