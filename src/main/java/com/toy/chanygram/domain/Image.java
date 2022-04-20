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
public class Image extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String caption;
    private String postImageUrl;

    @ManyToOne
    @JoinColumn(name = "User_ID")
    private User user;

    // TODO : like & comment 추가 필요
}
