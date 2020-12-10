package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Map;

/**
 * @author gg
 * @create 2020-12-07 下午3:06
 */
@Controller
public class ConfigController {

    @RequestMapping({"/", "/index.html"})
    public String login(){
        return "login";
    }

    @RequestMapping("/main.html")
    public String redirect(){
        return "dashboard";
    }


}
