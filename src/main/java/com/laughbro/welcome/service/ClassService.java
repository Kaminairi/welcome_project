package com.laughbro.welcome.service;

import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.class_params.ClassParams;

public interface ClassService {
    Result AddClass(ClassParams params);

    Result DeleteClass(String id);

    Result EditClass(ClassParams params);

    Result SearchClass(int page,int pagesize,String keyword);

    Result GetClass(int page, int pagesize);
}
