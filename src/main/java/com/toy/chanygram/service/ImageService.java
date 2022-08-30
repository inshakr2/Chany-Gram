package com.toy.chanygram.service;

import com.toy.chanygram.config.auth.PrincipalDetails;
import com.toy.chanygram.domain.*;
import com.toy.chanygram.dto.comment.CommentResponseDto;
import com.toy.chanygram.dto.image.*;
import com.toy.chanygram.dto.tag.TagWithId;
import com.toy.chanygram.exception.CustomValidationException;
import com.toy.chanygram.repository.ImageRepository;
import com.toy.chanygram.repository.ImageTagRepository;
import com.toy.chanygram.repository.TagRepository;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final TagRepository tagRepository;
    private final ImageTagRepository imageTagRepository;

    @Value("${custom.file.path}")
    private String uploadPath;
    @Value("${custom.story.page-size}")
    private int pageSize;

    @Transactional(readOnly = true)
    public List<ImageStoryDto> getImages(Long principalId, Long lastImageId) {
        PageRequest pageRequest = PageRequest.of(0, pageSize, Sort.by(DESC, "id"));

        Slice<Image> imageForStory = imageRepository.getImageForStory(principalId, lastImageId, pageRequest);
        List<ImageStoryDto> imageStoryDtoList = new ArrayList<>();

        HashMap<Long, List<Long>> tagMapper = new HashMap<>();

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
                            getCommentResponseDto(image.getComments()),
                            null)
                    );


                    tagMapper.put(image.getId(),
                            image.getTags().stream().
                                    map(ImageTag::getTag).
                                    map(Tag::getId).collect(Collectors.toList()));
                }
        );

        mappingTags(imageStoryDtoList, tagMapper);

        return imageStoryDtoList;
    }

    private void mappingTags(List<ImageStoryDto> imageStoryDtoList, HashMap<Long, List<Long>> tagMapper) {
        List<Long> tagIds = tagMapper.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        HashMap<Long, String> resultMap = new HashMap<>();
        List<TagWithId> result = tagRepository.findTagsWithIdByIds(tagIds);
        result.forEach(
                t -> {
                    if(!resultMap.containsKey(t.getTagId())) {
                        resultMap.put(t.getTagId(), t.getTag());
                    }
                }
        );

        imageStoryDtoList.forEach(
                i -> {
                    List<String> tagList = tagMapper.get(i.getImageId()).stream()
                            .map(resultMap::get)
                            .collect(Collectors.toList());

                    i.setTags(tagList);
                }
        );
    }

    public ImageDetailDto getImageDetail(Long principalId, Long imageId) {

        Image image = imageRepository.getImageForDetail(imageId).orElseThrow(
                () -> {
                    log.info("유효성 검사 실패 [존재하지 않는 이미지입니다.]");
                    return new CustomValidationException("존재하지 않는 이미지입니다.", null);
                }
        );

        User owner = image.getUser();

        ImageDetailDto imageDetailDto = new ImageDetailDto(
                image.getId(),
                image.getPostImageUrl(),
                image.getCaption(),
                getLikeStatus(image.getLikes(), principalId),
                image.getLikes().size(),
                owner.getId() == principalId,
                owner.getId(),
                owner.getUsername(),
                owner.getProfileImageUrl(),
                getCommentResponseDto(image.getComments()),
                getTagsList(image.getTags())
        );

        return imageDetailDto;
    }

    private List<String> getTagsList(List<ImageTag> imageTags) {
        List<String> tags = new ArrayList<>();
        List<Long> tagIds = imageTags.stream().map(i -> i.getTag().getId()).collect(Collectors.toList());

        return tagRepository.findTagsByIds(tagIds);

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

    public Long imageUpload(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
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
        Image entity = imageRepository.save(image);

        return entity.getId();
    }

    @Transactional(readOnly = true)
    public List<ImagePopularDto> getPopularImage(int page) {

        PageRequest pageRequest = PageRequest.of(page, 9);
        Slice<ImagePopularDto> images = imageRepository.fetchPopularImage(pageRequest);

        return images.getContent();
    }

    @Transactional(readOnly = true)
    public List<ImagesWithLikesDto> getProfileImage(int page, Long userId) {
        List<ImagesWithLikesDto> images = new ArrayList<>();

        PageRequest pageRequest = PageRequest.of(page, 9, Sort.by(DESC, "id"));
        List<Image> content = imageRepository.findImagesWithLikes(userId, pageRequest).getContent();

        content.forEach(
                c -> {
                    images.add(new ImagesWithLikesDto(c.getId(), c.getPostImageUrl(), c.getLikes().size()));
                }
        );

        return images;
    }


    public void deleteImage(Long imageId) {

        Image image = imageRepository.findById(imageId).orElseThrow(
                () -> {
                    log.info("유효성 검사 실패 [존재하지 않는 이미지입니다.]");
                    return new CustomValidationException("존재하지 않는 이미지입니다.", null);
                }
        );

        Path path = Paths.get(uploadPath + image.getPostImageUrl());
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            log.error("I/O error occurred during image delete : [" + imageId + "]");
            e.printStackTrace();
        }

        imageRepository.deleteById(imageId);

    }

    public ImageEditResponseDto getImageForEdit(Long imageId, Long principalId) {

        Image image = imageRepository.findImageWithOwner(imageId).orElseThrow(
                () -> {
                    log.info("유효성 검사 실패 [존재하지 않는 이미지입니다.]");
                    return new CustomValidationException("존재하지 않는 이미지입니다.", null);
                }
        );

        if (image.getUser().getId() != principalId) {
            throw new CustomValidationException("본인 게시물 이외의 이미지 수정은 불가합니다.", null);
        }

        StringJoiner sj = new StringJoiner(" ");
        image.getTags().forEach(
                tag -> {
                    sj.add("#" + tag.getTag().getTag());
                }
        );
        ImageEditResponseDto imageEditResponseDto = new ImageEditResponseDto(
                image.getId(), image.getPostImageUrl(), image.getCaption(), sj.toString());

        return imageEditResponseDto;

    }

    public void editImageDetail(Long imageId, Long principalId, ImageEditRequestDto imageEditRequestDto) {

        Image image = imageRepository.findImageWithOwner(imageId).orElseThrow(
                () -> {
                    log.info("유효성 검사 실패 [존재하지 않는 이미지입니다.]");
                    return new CustomValidationException("존재하지 않는 이미지입니다.", null);
                }
        );

        if (image.getUser().getId() != principalId) {
            throw new CustomValidationException("본인 게시물 이외의 이미지 수정은 불가합니다.", null);
        }

        image.editCaption(imageEditRequestDto.getCaption());
    }

    public List<ImageSearchResponseDto> searchImageByTag(ImageSearchPagingDto dto) {

        PageRequest pageRequest = PageRequest.of(0, 9, Sort.by(DESC, "id"));
        Slice<Image> imageSearchSlice = imageRepository.getImageFromTag(
                dto.getLastImageId(), dto.getTagId(), dto.getPopularIds(), pageRequest);
        List<ImageSearchResponseDto> imageSearchResults = new ArrayList<>();

        imageSearchSlice.forEach(
                image -> {
                    imageSearchResults.add(
                            new ImageSearchResponseDto(image.getId(), image.getUser().getId(), image.getPostImageUrl())
                    );
                }
        );

        return imageSearchResults;
    }

    public ImageSearchProfileDto getSearchProfile(String tag) {

        Tag tagEntity = tagRepository.findByTag(tag).orElseThrow(
                () -> {
                    log.info("유효성 검사 실패 [존재하지 않는 태그입니다.]");
                    return new CustomValidationException("존재하지 않는 태그입니다.", null);
                }
        );

        Long tagId = tagEntity.getId();

        boolean enoughImage = false;
        // Top N 쿼리 대안으로 페이징 처리
        PageRequest pageRequest = PageRequest.of(0, 10);
        Slice<ImagePopularDto> topImageSlice = imageRepository.getTopPopularImageByTag(tagId, pageRequest);
        List<ImagePopularDto> popularImages = new ArrayList<>(topImageSlice.getContent());

        if (popularImages.size() > 9) {
            enoughImage = true;
            popularImages.remove(9);
        }

        Long totalCnt = imageRepository.getTotalCountByTag(tagId);

        ImageSearchProfileDto imageSearchProfileDto = new ImageSearchProfileDto(
                tagId, totalCnt, popularImages, enoughImage);

        return imageSearchProfileDto;
    }
}
