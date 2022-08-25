package com.toy.chanygram.controller;

import com.toy.chanygram.exception.CustomValidationException;
import com.toy.chanygram.service.AuthService;
import com.toy.chanygram.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/auth/signin")
    public String signinForm(@RequestParam(value = "error", required = false)String error,
                             @RequestParam(value = "exception", required = false)String exception,
                             Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "/auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm() {
        return "/auth/signup";
    }

    @PostMapping("/auth/signup")
    public String signup(@ModelAttribute @Valid SignupDto signupDto,
                         Errors errors) {

        log.info("New SignUp Req : " + signupDto);
        authService.join(signupDto);

        return "/auth/signin";
    }
}
