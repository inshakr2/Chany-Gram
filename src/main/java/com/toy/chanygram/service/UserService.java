package com.toy.chanygram.service;

import com.toy.chanygram.domain.User;
import com.toy.chanygram.dto.user.UserProfileDto;
import com.toy.chanygram.dto.user.UserUpdateDto;
import com.toy.chanygram.exception.CustomValidationApiException;
import com.toy.chanygram.exception.CustomValidationException;
import com.toy.chanygram.repository.SubscribeRepository;
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
    private final SubscribeRepository subscribeRepository;

    public User userUpdate(long userId, UserUpdateDto userUpdateDto) {

        userUpdateDto.setPassword(encoder.encode(userUpdateDto.getPassword()));

        User findUser = userRepository.findUserWithImages(userId).orElseThrow(
                () -> {
                    log.info("유효성 검사 실패 [존재하지 않는 회원입니다.]");
                    return new CustomValidationApiException("존재하지 않는 회원입니다.");
                }
        );

        findUser.update(userUpdateDto);
        log.info("회원 정보 변경 : [" + findUser.getId() + "]" );
        return findUser;
    }

    @Transactional(readOnly = true)
    public UserProfileDto userProfile(Long pageUserId, Long principalId) {
        UserProfileDto userProfileDto = new UserProfileDto();

        User findUser = userRepository.findUserWithImages(pageUserId).orElseThrow(
                () -> {
                    log.info("유효성 검사 실패 [존재하지 않는 회원입니다.]");
                    return new CustomValidationException("존재하지 않는 회원입니다.", null);
                }
        );

        // User Meta 정보 세팅
        userProfileDto.setPageOwner(pageUserId == principalId);
        userProfileDto.setImageCount(findUser.getImages().size());
        userProfileDto.setUserId(findUser.getId());
        userProfileDto.setUsername(findUser.getUsername());
        userProfileDto.setUserAboutMe(findUser.getAboutMe());
        userProfileDto.setUserWebsite(findUser.getWebsite());
        userProfileDto.setImages(findUser.getImages());

        // 구독정보
        int subscribeState = subscribeRepository.checkSubscribeState(principalId, pageUserId);
        int following = subscribeRepository.countFollowing(pageUserId);
        int follower = subscribeRepository.countFollower(pageUserId);

        userProfileDto.setSubscribeState(subscribeState >= 1);
        userProfileDto.setFollowing(following);
        userProfileDto.setFollower(follower);

        return userProfileDto;
    }

}
