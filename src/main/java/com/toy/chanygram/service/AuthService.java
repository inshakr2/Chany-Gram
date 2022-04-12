package com.toy.chanygram.service;

import com.toy.chanygram.domain.user.User;
import com.toy.chanygram.domain.user.UserRepository;
import com.toy.chanygram.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder pwdEncoder;

    public User join(SignupDto signupDto) {
        String rawPwd = signupDto.getPassword();
        signupDto.setPassword(pwdEncoder.encode(rawPwd));

        User user = new User(signupDto);

        User userEntity = userRepository.save(user);
        log.info("New User Join Success : " + userEntity);
        return userEntity;
    }
}
