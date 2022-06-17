package com.toy.chanygram.service;

import com.toy.chanygram.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;


    public void likes(Long principalId, Long imageId) {
        log.info("USER " + principalId + " Likes To Image : " + imageId);
        likesRepository.fetchLikes(principalId, imageId);
    }

    public void unLikes(Long principalId, Long imageId) {
        log.info("USER " + principalId + " UnLikes To Image : " + imageId);
        likesRepository.fetchUnLikes(principalId, imageId);
    }
}
