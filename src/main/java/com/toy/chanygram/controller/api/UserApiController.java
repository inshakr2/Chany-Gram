package com.toy.chanygram.controller.api;

import com.toy.chanygram.config.auth.PrincipalDetails;
import com.toy.chanygram.domain.User;
import com.toy.chanygram.dto.CommonResponseDto;
import com.toy.chanygram.dto.subscribe.SubscribeResponseDto;
import com.toy.chanygram.dto.user.UserUpdateDto;
import com.toy.chanygram.service.SubscribeService;
import com.toy.chanygram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final SubscribeService subscribeService;

    @PutMapping("/api/user/{principalId}/profileImage")
    public ResponseEntity<?> profileImageUpdate(@PathVariable long principalId,
                                                MultipartFile profileImageFile,
                                                @AuthenticationPrincipal PrincipalDetails principalDetails) {
        User userEntity = userService.changeProfileImage(principalId, profileImageFile);
        principalDetails.setUser(userEntity);

        return new ResponseEntity<>(new CommonResponseDto<>(1, "프로필 사진 변경 성공", null), HttpStatus.OK);
    }

    @GetMapping("/api/user/{pageUserId}/follower")
    public ResponseEntity<?> followingList(@PathVariable long pageUserId,
                                           @AuthenticationPrincipal PrincipalDetails principalDetails) {

        List<SubscribeResponseDto> subscribeDto = subscribeService.
                getFollowerList(principalDetails.getUser().getId(), pageUserId);
        return new ResponseEntity<>(new CommonResponseDto<>(1, "구독 정보 리스트", subscribeDto), HttpStatus.OK);
    }

    @GetMapping("/api/user/{pageUserId}/subscribe")
    public ResponseEntity<?> subscribeList(@PathVariable long pageUserId,
                                           @AuthenticationPrincipal PrincipalDetails principalDetails) {

        List<SubscribeResponseDto> subscribeDto = subscribeService.
                                        getFollowingList(principalDetails.getUser().getId(), pageUserId);
        return new ResponseEntity<>(new CommonResponseDto<>(1, "구독 정보 리스트", subscribeDto), HttpStatus.OK);
    }

    @PatchMapping("/api/user/{id}")
    public CommonResponseDto<?> update(@PathVariable long id,
                                       @ModelAttribute @Valid UserUpdateDto userUpdateDto,
                                       Errors errors,
                                       @AuthenticationPrincipal PrincipalDetails principalDetails) {

        User user = userService.userUpdate(id, userUpdateDto);
        principalDetails.setUser(user);
        return new CommonResponseDto<>(1, "회원정보 수정완료", null);
    }

    @DeleteMapping("/api/user/{principalId}")
    public CommonResponseDto<?> leaveMembership(@PathVariable long principalId) {

        userService.withdrawalMember(principalId);
        return new CommonResponseDto<>(1, "회원탈퇴 완료", null);
    }
}
