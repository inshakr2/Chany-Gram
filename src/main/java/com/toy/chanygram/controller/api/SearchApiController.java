package com.toy.chanygram.controller.api;

import com.toy.chanygram.config.auth.PrincipalDetails;
import com.toy.chanygram.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SearchApiController {

    private final SearchService searchService;

    @PostMapping("/api/search")
    public ResponseEntity<?> searchResultList(@RequestParam String searchKeywords,
                                              @AuthenticationPrincipal PrincipalDetails principalDetails) {


        searchService.getResultFromKeywords(searchKeywords, principalDetails.getUser().getId());

        return null;
    }
}
