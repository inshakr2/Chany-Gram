package com.toy.chanygram.service;

import com.toy.chanygram.domain.Tag;
import com.toy.chanygram.dto.image.ImageUploadDto;
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

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final ImageTagRepository imageTagRepository;

    public void mappingImage(ImageUploadDto imageUploadDto, Long imageId) {
        List<String> tagString = splitTag(imageUploadDto.getTag());

        for (String t : tagString) {
            Tag tag = tagRepository.findByTag(t).orElseGet(
                    () -> tagRepository.save(new Tag(t))
            );

            imageTagRepository.mappingImageWithTags(imageId, tag.getId());
        }

    }

    public void editMappingImage(Long imageId, String orgTag, String newTag) {

        System.out.println("orgTag = " + orgTag);
        System.out.println("newTag = " + newTag);
        if (orgTag != newTag) {

            List<String> newTags = splitTag(newTag);

            // image tag 매핑 정보 초기화 후 재 매핑
            imageTagRepository.deleteByImageId(imageId);

            for (String t : newTags) {
                Tag tag = tagRepository.findByTag(t).orElseGet(
                        () -> tagRepository.save(new Tag(t))
                );

                imageTagRepository.mappingImageWithTags(imageId, tag.getId());
            }

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
