package com.toy.chanygram.handler;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String message;

        if (exception instanceof BadCredentialsException) {
            message = "잘못된 계정 정보입니다. 다시 확인하세요.";
        } else if (exception instanceof UsernameNotFoundException) {
            message = "입력한 사용자 이름을 사용하는 계정을 찾을 수 없습니다. 사용자 이름을 확인하고 다시 시도하세요.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            message = "서버 문제로 인해 로그인 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            message = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
        } else {
            message = "알 수 없는 이유로 로그인에 실패하였습니다. 관리자에게 문의하세요.";
        }
        message = URLEncoder.encode(message, "UTF-8");

        setDefaultFailureUrl("/auth/signin?error=true&exception="+message);
        super.onAuthenticationFailure(request, response, exception);

    }
}
