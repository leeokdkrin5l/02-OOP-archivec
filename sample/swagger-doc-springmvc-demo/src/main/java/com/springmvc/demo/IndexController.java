package com.springmvc.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-18 下午5:05
 */
@Controller
public class IndexController {
    @PostConstruct
    public void init() {
        System.out.println("123123");
    }

    @RequestMapping(value = { "/", "" })
    @ResponseBody
    public String index() {
        return "123";
    }
}