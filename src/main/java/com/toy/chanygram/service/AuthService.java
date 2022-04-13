package com.toy.chanygram.service;

import com.toy.chanygram.domain.User;
import com.toy.chanygram.exception.CustomValidationException;
import com.toy.chanygram.repository.UserRepository;
import com.toy.chanygram.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder pwdEncoder;

    public User join(SignupDto signupDto) {

        Optional<User> findUser = userRepository.findByUsername(signupDto.getUsername());

        if (findUser.isEmpty()) {
            String rawPwd = signupDto.getPassword();
            signupDto.setPassword(pwdEncoder.encode(rawPwd));

            User user = new User(signupDto);

            User userEntity = userRepository.save(user);
            log.info("New User Join Success : " + userEntity);
            return userEntity;
        } else {
            String msg = "이미 존재하는 회원입니다.";
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("username", msg);
            log.info("유효성 검사 실패 [" + msg + "]");
            throw new CustomValidationException(msg, errorMap);
        }
    }
}
