package com.laughbro.welcome.controller;

import com.laughbro.welcome.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class TestController {



    @GetMapping("/geturl")
    public void geturl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取服务器的URL
        StringBuffer serverURL = request.getRequestURL();
        String contextPath = request.getContextPath();

        // 构建完整的服务器URL
        String fullServerURL = serverURL.toString().replace(request.getRequestURI(), contextPath);

        // 将服务器URL字符串作为响应返回给客户端
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(fullServerURL);
    }
}
