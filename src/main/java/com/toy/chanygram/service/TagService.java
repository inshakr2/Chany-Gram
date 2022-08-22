package com.toy.chanygram.service;

import com.toy.chanygram.domain.Tag;
import com.toy.chanygram.dto.image.ImageUploadDto;
import com.toy.chanygram.dto.tag.TagWithId;
import com.toy.chanygram.repository.ImageTagRepository;
import com.toy.chanygram.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final ImageTagRepository imageTagRepository;

    public void mappingImage(ImageUploadDto imageUploadDto, Long imageId) {
        List<String> requestTags = splitTag(imageUploadDto.getTag());
        List<TagWithId> existsTagWithId = tagRepository.findTagsByTags(requestTags);

        List<String> existsTag = existsTagWithId.stream().map(TagWithId::getTag).collect(Collectors.toList());
        requestTags.removeIf(t -> existsTag.contains(t));
        for (String t : requestTags) {
            Tag newTag = tagRepository.save(new Tag(t));

            existsTagWithId.add(new TagWithId(newTag.getId(), t));
        }

        existsTagWithId.forEach(
                t -> {
                    imageTagRepository.mappingImageWithTags(imageId, t.getTagId());
                }
        );

    }

    public void editMappingImage(Long imageId, String orgTag, String newTag) {

        if (orgTag != newTag) {

            // image tag 매핑 정보 초기화 후 재 매핑
            imageTagRepository.deleteByImageId(imageId);

            List<String> requestTags = splitTag(newTag);
            List<TagWithId> existsTagWithId = tagRepository.findTagsByTags(requestTags);

            List<String> existsTag = existsTagWithId.stream().map(TagWithId::getTag).collect(Collectors.toList());
            requestTags.removeIf(t -> existsTag.contains(t));

            for (String t : requestTags) {
                Tag newTagEntity = tagRepository.save(new Tag(t));

                existsTagWithId.add(new TagWithId(newTagEntity.getId(), t));
            }

            existsTagWithId.forEach(
                    t -> {
                        imageTagRepository.mappingImageWithTags(imageId, t.getTagId());
                    }
            );
        }
    }

    private List<String> splitTag(String rawTag) {

        rawTag = rawTag.endsWith(" ") ? rawTag : rawTag + " ";
        List<String> tags = new ArrayList<>();

        Pattern p = Pattern.compile("\\#([a-zA-Zㄱ-ㅎ가-힣0-9]*)\\s{1}");
        Matcher m = p.matcher(rawTag);

        while (m.find()) {
            tags.add(m.group(1));
        }

        return tags;
    }
}
