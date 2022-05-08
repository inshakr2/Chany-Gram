package com.toy.chanygram.controller.api;

import com.toy.chanygram.config.auth.PrincipalDetails;
import com.toy.chanygram.domain.Image;
import com.toy.chanygram.dto.CommonResponseDto;
import com.toy.chanygram.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageApiController {

    private final ImageService imageService;


    @GetMapping("/api/image")
    public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<Image> images = imageService.getImages(principalDetails.getUser().getId());

        return new ResponseEntity<>(
                new CommonResponseDto<>(1, "스토리 가져오기 성공", images), HttpStatus.OK);
    }
}
