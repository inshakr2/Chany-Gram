package com.toy.chanygram.service;

import com.toy.chanygram.domain.User;
import com.toy.chanygram.dto.user.UserUpdateDto;
import com.toy.chanygram.exception.CustomValidationApiException;
import com.toy.chanygram.exception.CustomValidationException;
import com.toy.chanygram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public User userUpdate(long userId, UserUpdateDto userUpdateDto) {

        userUpdateDto.setPassword(encoder.encode(userUpdateDto.getPassword()));

        User findUser = userRepository.findById(userId).orElseThrow(
                () -> {
                    log.info("유효성 검사 실패 [존재하지 않는 회원입니다.]");
                    return new CustomValidationApiException("존재하지 않는 회원입니다.");
                }
        );

        findUser.update(userUpdateDto);

        return findUser;
    }

    public User userProfile(Long userId) {
        User findUser = userRepository.findUserForProfile(userId).orElseThrow(
                () -> {
                    log.info("유효성 검사 실패 [존재하지 않는 회원입니다.]");
                    return new CustomValidationException("존재하지 않는 회원입니다.", null);
                }
        );

        return findUser;
    }

}
