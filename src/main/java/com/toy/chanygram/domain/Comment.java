package com.toy.chanygram.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Setter(PROTECTED) @Getter
@NoArgsConstructor(access = PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String content;

    @JoinColumn(name = "User_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"images"})
    private User user;

    @JoinColumn(name = "Image_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Image image;

    public
    Comment(String content, User user, Image image) {
        this.content = content;
        this.user = user;
        this.image = image;
    }
}
