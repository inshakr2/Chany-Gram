package com.toy.chanygram.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter(PROTECTED)
@NoArgsConstructor(access = PROTECTED)
public class Tag extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tag;

    @JsonIgnoreProperties({"tag"})
    @OneToMany(mappedBy = "tag")
    List<ImageTag> images = new ArrayList<>();

    public Tag(String tag) {
        this.tag = tag;
    }
}
