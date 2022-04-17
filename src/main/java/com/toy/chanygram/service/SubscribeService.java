package com.toy.chanygram.service;

import com.toy.chanygram.domain.Subscribe;
import com.toy.chanygram.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    public void subscribe(Long fromUserId, Long toUserId) {
        subscribeRepository.subscribe(fromUserId, toUserId);
    }

    public void unSubscribe(Long fromUserId, Long toUserId) {
        subscribeRepository.unSubscribe(fromUserId, toUserId);
    }
}
