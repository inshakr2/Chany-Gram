package com.toy.chanygram.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.toy.chanygram.dto.image.ImageUploadDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter(PROTECTED)
@ToString
@NoArgsConstructor(access = PROTECTED)
public class Image extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String caption;
    private String postImageUrl;

    @JsonIgnoreProperties({"images"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_ID")
    private User user;

    @OneToMany(mappedBy = "image")
    private List<Likes> likes;

    // TODO : comment 추가 필요

    public Image(String caption, String imageFullPath, User user) {
        this.caption = caption;
        this.postImageUrl = imageFullPath;
        this.user = user;
    }
}
