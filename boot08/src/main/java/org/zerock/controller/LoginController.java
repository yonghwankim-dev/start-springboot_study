package org.zerock.controller;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log
public class LoginController {
    @GetMapping("/login")
    public void login(){
        log.info("login");
    }

    @GetMapping("/accessDenied")
    public void accessDenied(){
        log.info("accessDenied");
    }

    @GetMapping("/logout")
    public void logout(){
        log.info("logout");
    }
}
