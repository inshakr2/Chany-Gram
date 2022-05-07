package com.toy.chanygram.service;

import com.toy.chanygram.dto.subscribe.SubscribeResponseDto;
import com.toy.chanygram.exception.CustomApiException;
import com.toy.chanygram.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    public void subscribe(Long fromUserId, Long toUserId) {
        try {
            log.info("USER " + fromUserId + " Subscribed to USER " + toUserId);
            subscribeRepository.subscribe(fromUserId, toUserId);
        } catch (Exception e) {
            log.info("USER " + fromUserId +" has already subscribed to USER " + toUserId);
            throw new CustomApiException("이미 구독한 사용자입니다.");
        }
    }

    public void unSubscribe(Long fromUserId, Long toUserId) {
        log.info("USER " + fromUserId + " UnSubscribed to USER " + toUserId);
        subscribeRepository.unSubscribe(fromUserId, toUserId);
    }

    @Transactional(readOnly = true)
    public List<SubscribeResponseDto> getFollowingList(Long principalId, Long pageUserId) {
        List<SubscribeResponseDto> subscribeList = subscribeRepository.followingList(principalId, pageUserId).get();

        return subscribeList;
    }

    @Transactional(readOnly = true)
    public List<SubscribeResponseDto> getFollowerList(Long principalId, Long pageUserId) {
        List<SubscribeResponseDto> subscribeList = subscribeRepository.followerList(principalId, pageUserId).get();

        return subscribeList;
    }
}

