package com.toy.chanygram.controller.api;

import com.toy.chanygram.config.auth.PrincipalDetails;
import com.toy.chanygram.dto.CommonResponseDto;
import com.toy.chanygram.dto.image.ImageSearchResponseDto;
import com.toy.chanygram.dto.image.ImageStoryDto;
import com.toy.chanygram.dto.image.ImageDetailDto;
import com.toy.chanygram.service.ImageService;
import com.toy.chanygram.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageApiController {

    private final ImageService imageService;
    private final LikesService likesService;

    // @PageableDefault(size = 3, sort = "createdDate", direction = DESC) Pageable pageable
    @GetMapping("/api/image")
    public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                        @RequestParam Long lastImageId) {
        List<ImageStoryDto> images = imageService.getImages(principalDetails.getUser().getId(), lastImageId);

        return new ResponseEntity<>(
                new CommonResponseDto<>(1, "스토리 가져오기 성공", images), HttpStatus.OK);
    }

    @GetMapping("/api/image/{imageId}")
    public ResponseEntity<?> imageDetail(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                         @PathVariable Long imageId) {
        ImageDetailDto image = imageService.getImageDetail(principalDetails.getUser().getId(), imageId);

        return new ResponseEntity<>(
                new CommonResponseDto<>(1, "이미지 상세정보 가져오기 성공", image), HttpStatus.OK);
    }

    @DeleteMapping("/api/image/{imageId}")
    public ResponseEntity<?> imageDelete(@PathVariable Long imageId) {

        imageService.deleteImage(imageId);

        return new ResponseEntity<>(
                new CommonResponseDto<>(1, "이미지 삭제 성공", null), HttpStatus.OK);

    }

    @PostMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> imageLikes(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                        @PathVariable Long imageId) {

        likesService.likes(principalDetails.getUser().getId(), imageId);

        return new ResponseEntity<>(
                new CommonResponseDto<>(1, "이미지 좋아요 성공", null), HttpStatus.CREATED);

    }

    @DeleteMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> imageUnLikes(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                          @PathVariable Long imageId) {

        likesService.unLikes(principalDetails.getUser().getId(), imageId);

        return new ResponseEntity<>(
                new CommonResponseDto<>(1, "이미지 좋아요 취소 성공", null), HttpStatus.OK);

    }

    @GetMapping("/api/image/search")
    public ResponseEntity<?> imageSearch(@RequestParam Long lastImageId,
                                         @RequestParam Long tagId) {

        List<ImageSearchResponseDto> images = imageService.searchImageByTag(lastImageId, tagId);

        return new ResponseEntity<>(
                new CommonResponseDto<>(1, "태그 검색 결과 가져오기 성공", images), HttpStatus.OK);

    }
}
