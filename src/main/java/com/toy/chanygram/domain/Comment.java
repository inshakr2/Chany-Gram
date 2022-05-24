package com.toy.chanygram.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter(PROTECTED)
@ToString
@NoArgsConstructor(access = PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String content;

    @JoinColumn(name = "User_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "Image_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Image image;
}
