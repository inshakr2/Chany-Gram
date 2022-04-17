package com.toy.chanygram.controller.api;

import com.toy.chanygram.config.auth.PrincipalDetails;
import com.toy.chanygram.dto.CommonResponseDto;
import com.toy.chanygram.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SubscribeApiController {

    private final SubscribeService subscribeService;

    @PostMapping("/api/subscribe/{toUserId}")
    public ResponseEntity<?> subscribe(@PathVariable Long toUserId,
                                       @AuthenticationPrincipal PrincipalDetails principalDetails) {
        subscribeService.subscribe(principalDetails.getUser().getId(), toUserId);

        return new ResponseEntity<>(new CommonResponseDto<>(1, "구독하기 성공", null), HttpStatus.OK);
    }

    @DeleteMapping("/api/subscribe/{toUserId}")
    public ResponseEntity<?> unSubscribe(@PathVariable Long toUserId,
                                         @AuthenticationPrincipal PrincipalDetails principalDetails) {
        subscribeService.unSubscribe(principalDetails.getUser().getId(), toUserId);

        return new ResponseEntity<>(new CommonResponseDto<>(1, "구독취소 성공", null), HttpStatus.OK);
    }
}
