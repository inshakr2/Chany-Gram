package com.toy.chanygram.controller.api;

import com.toy.chanygram.config.auth.PrincipalDetails;
import com.toy.chanygram.domain.User;
import com.toy.chanygram.dto.CommonResponseDto;
import com.toy.chanygram.dto.user.UserUpdateDto;
import com.toy.chanygram.exception.CustomValidationApiException;
import com.toy.chanygram.exception.CustomValidationException;
import com.toy.chanygram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PatchMapping("/api/user/{id}")
    public CommonResponseDto<?> update(@PathVariable long id,
                                       @ModelAttribute @Valid UserUpdateDto userUpdateDto,
                                       Errors errors,
                                       @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (errors.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for(FieldError fieldError : errors.getFieldErrors()) {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                log.info("유효성 검사 실패 - 회원정보 수정 [" + fieldError.getField() +"] : [" + fieldError.getDefaultMessage() + "]");
            }

            throw new CustomValidationApiException("유효성 검사 실패 - 회원정보 수정", errorMap);
        }

        User user = userService.userUpdate(id, userUpdateDto);
        principalDetails.setUser(user);
        return new CommonResponseDto<>(1, "회원정보 수정완료", user);
    }
}
