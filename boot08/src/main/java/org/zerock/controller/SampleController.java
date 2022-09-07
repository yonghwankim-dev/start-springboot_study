package org.zerock.controller;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log
public class SampleController {

    @GetMapping("/")
    public String index(){
        log.info("index");
        return "index";
    }

    @RequestMapping("/guest")
    public String forGuest(){
        return "guest";
    }

    @RequestMapping("/manager")
    public String forManager(){
        return "manager";
    }

    @RequestMapping("/admin")
    public String forAdmin(){
        return "admin";
    }

}
