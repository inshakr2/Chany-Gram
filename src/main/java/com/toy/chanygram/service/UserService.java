package com.toy.chanygram.service;

import com.toy.chanygram.domain.User;
import com.toy.chanygram.dto.user.UserUpdateDto;
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

    public User userUpdate(long id, UserUpdateDto userUpdateDto) {

        userUpdateDto.setPassword(encoder.encode(userUpdateDto.getPassword()));

        //TODO : Exception 처리
        User findUser = userRepository.findById(id).get();
        findUser.update(userUpdateDto);

        return findUser;
    }

}
