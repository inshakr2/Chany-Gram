package com.toy.chanygram.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter(PROTECTED)
@NoArgsConstructor(access = PROTECTED)
public class ImageTag extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "Image_ID")
    private Image image;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "Tag_ID")
    private Tag tag;

}
