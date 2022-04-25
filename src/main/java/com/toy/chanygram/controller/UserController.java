package com.toy.chanygram.controller;

import com.toy.chanygram.domain.User;
import com.toy.chanygram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{id}")
    public String profile(@PathVariable Long id,
                          Model model) {

        User user = userService.userProfile(id);
        model.addAttribute("user", user);
        return "/user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String update(@PathVariable Long id) {
        return "/user/update";
    }
}
