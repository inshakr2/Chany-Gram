package com.toy.chanygram.service;

import com.toy.chanygram.config.auth.PrincipalDetails;
import com.toy.chanygram.domain.Comment;
import com.toy.chanygram.domain.Image;
import com.toy.chanygram.domain.Likes;
import com.toy.chanygram.dto.comment.CommentResponseDto;
import com.toy.chanygram.dto.image.ImagePopularDto;
import com.toy.chanygram.dto.image.ImageStoryDto;
import com.toy.chanygram.dto.image.ImageUploadDto;
import com.toy.chanygram.dto.image.ImageDetailDto;
import com.toy.chanygram.exception.CustomValidationException;
import com.toy.chanygram.repository.CommentRepository;
import com.toy.chanygram.repository.ImageRepository;
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
import java.util.stream.Collectors;

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

        //TODO: like 정보와 comment 정보 fetch 시킬 수 있도록 개선 필요, 현재 우선 page size 상향 조정 하였음.
        imageForStory.forEach(
                image -> {
                    imageStoryDtoList.add(new ImageStoryDto(
                            image.getId(),
                            image.getPostImageUrl(),
                            image.getCaption(),
                            getLikeStatus(image.getLikes(), principalId),
                            image.getLikes().size(),
                            image.getUser().getId(),
                            image.getUser().getUsername(),
                            image.getUser().getProfileImageUrl(),
                            getCommentResponseDto(image.getComments())
                    ));
                }
        );

        return imageStoryDtoList;
    }

    public ImageDetailDto getImageDetail(Long principalId, Long imageId) {

        Image image = imageRepository.getImageForDetail(principalId, imageId).orElseThrow(
                () -> {
                    log.info("유효성 검사 실패 [존재하지 않는 이미지입니다.]");
                    return new CustomValidationException("존재하지 않는 이미지입니다.", null);
                }
        );

        ImageDetailDto imageDetailDto = new ImageDetailDto(
                image.getId(),
                image.getPostImageUrl(),
                image.getCaption(),
                getLikeStatus(image.getLikes(), principalId),
                image.getLikes().size(),
                image.getUser().getId(),
                image.getUser().getUsername(),
                image.getUser().getProfileImageUrl(),
                getCommentResponseDto(image.getComments())
        );


        return imageDetailDto;
    }

    private List<CommentResponseDto> getCommentResponseDto(List<Comment> comments) {
        List<CommentResponseDto> dto = new ArrayList<>();
        comments.forEach(
                comment -> {
                    dto.add(new CommentResponseDto(comment.getId(), comment.getUser().getId(),
                            comment.getUser().getUsername(), comment.getContent()));
                }
        );

        return dto.stream().sorted().collect(Collectors.toList());
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

    public void deleteImage(Long imageId) {

        imageRepository.deleteById(imageId);

    }
}
