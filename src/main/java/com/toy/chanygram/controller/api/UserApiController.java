package com.toy.chanygram.controller.api;

import com.toy.chanygram.config.auth.PrincipalDetails;
import com.toy.chanygram.domain.User;
import com.toy.chanygram.dto.CommonResponseDto;
import com.toy.chanygram.dto.user.UserUpdateDto;
import com.toy.chanygram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PatchMapping("/api/user/{id}")
    public CommonResponseDto<?> update(@PathVariable long id,
                                       @ModelAttribute UserUpdateDto userUpdateDto,
                                       @AuthenticationPrincipal PrincipalDetails principalDetails) {
        User user = userService.userUpdate(id, userUpdateDto);
        principalDetails.setUser(user);
        return new CommonResponseDto<>(1, "회원정보 수정완료", user);
    }
}
