package com.toy.chanygram.controller.api;

import com.toy.chanygram.config.auth.PrincipalDetails;
import com.toy.chanygram.dto.CommonResponseDto;
import com.toy.chanygram.dto.search.SearchResultDto;
import com.toy.chanygram.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SearchApiController {

    private final SearchService searchService;

    @PostMapping("/api/search")
    public ResponseEntity<?> searchResultList(@RequestParam String searchKeywords,
                                              @AuthenticationPrincipal PrincipalDetails principalDetails) {


        List<SearchResultDto> resultFromKeywords = searchService.getResultFromKeywords(searchKeywords, principalDetails.getUser().getId());

        return new ResponseEntity<>(new CommonResponseDto<>(1, "search api 결과 반환 성공", resultFromKeywords), HttpStatus.OK);
    }
}
