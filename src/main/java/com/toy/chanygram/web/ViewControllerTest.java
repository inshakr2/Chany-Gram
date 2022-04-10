package com.toy.chanygram.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewControllerTest {

    @GetMapping("/auth/signin")
    public String signin() {
        return "/auth/signin";
    }
}
