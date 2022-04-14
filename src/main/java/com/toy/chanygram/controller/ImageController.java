package com.toy.chanygram.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ImageController {

    @GetMapping({"/", "/image/story"})
    public String story() {
        return "/image/story";
    }

}
