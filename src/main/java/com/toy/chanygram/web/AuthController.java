package com.toy.chanygram.web;

import com.toy.chanygram.domain.user.User;
import com.toy.chanygram.service.AuthService;
import com.toy.chanygram.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/auth/signin")
    public String signinForm() {
        return "/auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm() {
        return "/auth/signup";
    }

    @PostMapping("/auth/signup")
    public String signup(@ModelAttribute SignupDto signupDto) {

        log.info("New SignUp Req : " + signupDto);
        authService.join(signupDto);

        return "/auth/signin";
    }
}
