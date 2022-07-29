package com.toy.chanygram.controller;

import com.toy.chanygram.config.auth.PrincipalDetails;
import com.toy.chanygram.dto.image.ImageEditDto;
import com.toy.chanygram.dto.image.ImagePopularDto;
import com.toy.chanygram.dto.image.ImageUploadDto;
import com.toy.chanygram.exception.CustomValidationException;
import com.toy.chanygram.service.ImageService;
import com.toy.chanygram.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final TagService tagService;

    @GetMapping({"/", "/images/story"})
    public String story() {
        return "/images/story";
    }

    @GetMapping({"/images/{imageId}/edit"})
    public String editImage(@PathVariable Long imageId,
                            @AuthenticationPrincipal PrincipalDetails principalDetails,
                            Model model) {

        ImageEditDto imageEditDto = imageService.getImageForEdit(imageId, principalDetails.getUser().getId());
        model.addAttribute("image", imageEditDto);

        return "/images/edit";
    }

    @GetMapping("/images/popular")
    public String popular(Model model) {

        List<ImagePopularDto> popularImage = imageService.getPopularImage();
        model.addAttribute("popularImage", popularImage);

        return "/images/popular";
    }

    @GetMapping("/images/upload")
    public String upload() {
        return "/images/upload";
    }

    @PostMapping("/images")
    public String imageUpload(@ModelAttribute ImageUploadDto imageUploadDto,
                              @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (imageUploadDto.getFile().isEmpty()) {
            log.warn("No image attached");
            throw new CustomValidationException("이미지가 첨부되지 않았습니다.", null);
        }
        Long imageId = imageService.imageUpload(imageUploadDto, principalDetails);
        tagService.mappingImage(imageUploadDto, imageId);

        return "redirect:/user/" + principalDetails.getUser().getId();
    }
}
