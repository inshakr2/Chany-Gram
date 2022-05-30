package com.toy.chanygram.service;

import com.toy.chanygram.config.auth.PrincipalDetails;
import com.toy.chanygram.domain.Image;
import com.toy.chanygram.domain.Likes;
import com.toy.chanygram.dto.image.ImagePopularDto;
import com.toy.chanygram.dto.image.ImageStoryDto;
import com.toy.chanygram.dto.image.ImageUploadDto;
import com.toy.chanygram.repository.CommentRepository;
import com.toy.chanygram.repository.ImageRepository;
import com.toy.chanygram.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final CommentRepository commentRepository;

    @Value("${custom.file.path}")
    private String uploadPath;
    @Value("${custom.story.page-size}")
    private int pageSize;

    @Transactional(readOnly = true)
    public List<ImageStoryDto> getImages(Long principalId, Long lastImageId) {
        PageRequest pageRequest = PageRequest.of(0, pageSize, Sort.by(DESC, "id"));

        Slice<Image> imageForStory = imageRepository.getImageForStory(principalId, lastImageId, pageRequest);
        List<ImageStoryDto> imageStoryDtoList = new ArrayList<>();

        // 각 Image 별로 댓글 및 좋아요 정보는 join으로 가져올 수 없어 각 repository에 요청.. 대신 page size 상향 조정함

        imageForStory.forEach(
                image -> {
                    imageStoryDtoList.add(new ImageStoryDto(
                            image.getId(),
                            image.getPostImageUrl(),
                            image.getCaption(),
                            getLikeStatus(image.getLikes(), principalId),
                            image.getLikes().size(),
                            image.getUser().getUsername(),
                            image.getUser().getProfileImageUrl(),
                            commentRepository.findByImageForStory(image.getId())
                    ));
                }
        );

        return imageStoryDtoList;
    }

    private boolean getLikeStatus(List<Likes> likes, Long principalId) {
        List<Long> likeUserId = new ArrayList<>();

        likes.forEach(
                like -> {
                    likeUserId.add(like.getUser().getId());
                }
        );

        return likeUserId.contains(principalId);
    }

    public void imageUpload(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();

        String imageFullPath = uploadPath + imageFileName;
        Path imagePath = Paths.get(imageFullPath);

        try {
            log.info("Try to upload [" + imageFileName +"] image to ${file.path}");
            Files.write(imagePath, imageUploadDto.getFile().getBytes());
        } catch (IOException e) {
            log.error("I/O error occurred during image upload : [" + imageFileName + "]");
            e.printStackTrace();
        }

        Image image = new Image(imageUploadDto.getCaption(), imageFileName, principalDetails.getUser());
        imageRepository.save(image);
    }

    @Transactional(readOnly = true)
    public List<ImagePopularDto> getPopularImage() {

        List<ImagePopularDto> images = imageRepository.fetchPopularImage();
        return images;
    }
}
