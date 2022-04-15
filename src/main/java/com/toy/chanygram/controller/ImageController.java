package com.toy.chanygram.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ImageController {

    @GetMapping({"/", "/images/story"})
    public String story() {
        return "/images/story";
    }

    @GetMapping("/images/popular")
    public String popular() {
        return "/images/popular";
    }

    @GetMapping("/images/upload")
    public String upload() {
        return "/images/upload";
    }
}
