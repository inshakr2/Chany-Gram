package com.toy.chanygram.controller;

import com.toy.chanygram.config.auth.PrincipalDetails;
import com.toy.chanygram.dto.image.ImageUploadDto;
import com.toy.chanygram.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping({"/", "/images/story"})
    public String story() {
        return "/images/story";
    }

    @GetMapping("/images/popular")
    public String popular() {
        return "/images/popular";
    }

    @GetMapping("/images/upload")
    public String upload() {
        return "/images/upload";
    }

    @PostMapping("/images")
    public String imageUpload(@ModelAttribute ImageUploadDto imageUploadDto,
                              @AuthenticationPrincipal PrincipalDetails principalDetails) {

        imageService.imageUpload(imageUploadDto, principalDetails);
        return "redirect:/user/" + principalDetails.getUser().getId();
    }
}
