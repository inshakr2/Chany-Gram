package com.toy.chanygram.service;


import com.toy.chanygram.dto.search.SearchResultDto;
import com.toy.chanygram.dto.user.UserSearchResultDto;
import com.toy.chanygram.repository.TagRepository;
import com.toy.chanygram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j @Service
@RequiredArgsConstructor
public class SearchService {

    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    public void getResultFromKeywords(String searchKeywords, Long principalId) {

        // Tag 검색
        List<SearchResultDto> searchTagResult = tagRepository.searchByTag(searchKeywords);
        //searchTagResult.removeIf(f -> f.getCount() == 0);

        // User 검색
        List<SearchResultDto> searchUserResult = userRepository.searchByUsername(searchKeywords, principalId);

        //searchUserResult.removeIf(f -> f.getUserId().equals(principalId));

        System.out.println("searchTagResult = " + searchTagResult);
        System.out.println("searchUserResult = " + searchUserResult);

        List<SearchResultDto> result =
                // 검색 결과 filter : User -> 본인 아이디 제외 & Tag -> 게시물 0개인 경우 제외
                //  sort : count 값을 통해 User -> 팔로워 수 & Tag -> 게시물 수
                Stream.concat(searchTagResult.stream(), searchUserResult.stream())
                        .filter(f -> f.isTagResult() ? f.getCount() != 0 : !f.getUserId().equals(principalId))
                        .sorted()
                        .collect(Collectors.toList());

        System.out.println("result = " + result);
    }
}
