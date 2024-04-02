package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.ClassService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.class_params.ClassDeleteParams;
import com.laughbro.welcome.vo.params.class_params.ClassParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClassController {
    @Autowired
    private ClassService classService;

    @PostMapping("/admin/add/class")
    public Result AddClass(@RequestBody ClassParams params){
        return classService.AddClass(params);
    }

    @DeleteMapping("/admin/delete/class")
    public Result DeleteClass(@RequestBody ClassDeleteParams params){
        return classService.DeleteClass(params.getId());
    }

    @PatchMapping("/admin/edit/class")
    public Result EditClass(@RequestBody ClassParams params){
        return classService.EditClass(params);
    }

    @GetMapping("/admin/search/class")
    public Result SearchClass(int page,int pagesize,String keyword){
        return classService.SearchClass(page,pagesize,keyword);
    }

    @GetMapping("/admin/get/class")
    public Result GetClass(int page,int pagesize){
        return classService.GetClass(page,pagesize);
    }
}
