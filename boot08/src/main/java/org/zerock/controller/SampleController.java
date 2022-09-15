package org.zerock.controller;

import lombok.extern.java.Log;
import org.springframework.security.access.annotation.Secured;
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
        log.info("forGuest");
        return "guest";
    }

    @RequestMapping("/manager")
    public String forManager(){
        log.info("forManager");
        return "manager";
    }

    @RequestMapping("/admin")
    public String forAdmin(){
        log.info("forAdmin");
        return "admin";
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping("/adminSecret")
    public void forAdminSecret(){
        log.info("forAdminSecret");
    }

    @Secured({"ROLE_MANAGER"})
    @RequestMapping("/managerSecret")
    public void forManagerSecret(){
        log.info("forManagerSecret");
    }
}
