package com.toy.chanygram.dto.search;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchResultDto implements Comparable<SearchResultDto> {

    // 검색 결과가 Tag = True / User = False
    private boolean tagResult;

    private String tag;

    private Long userId;
    private String username; // 아이디
    private String name; // 실제 이름
    private String userProfileImageUrl;
    private boolean subscribeStatus;

    // 최종 반환시 정렬을 위한 필드로 User = 팔로워 수, Tag = 게시물 수
    private Long count;

    @Override
    public int compareTo(SearchResultDto o) {
        return Long.compare(o.count, this.count);
    }

    public SearchResultDto(boolean tagResult, String tag, Long count) {
        this.tagResult = tagResult;
        this.tag = tag;
        this.count = count;
    }

    public SearchResultDto(boolean tagResult, Long userId, String username, String name, String userProfileImageUrl, boolean subscribeStatus, Long count) {
        this.tagResult = tagResult;
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.userProfileImageUrl = userProfileImageUrl;
        this.subscribeStatus = subscribeStatus;
        this.count = count;
    }
}
