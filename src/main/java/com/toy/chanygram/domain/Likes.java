package com.toy.chanygram.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter(PROTECTED) @ToString
@NoArgsConstructor(access = PROTECTED)
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "likes_uk",
                        columnNames = {"FromUser_ID", "ToImage_ID"}
                )
        }
)
public class Likes extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ToImage_ID")
    private Image image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FromUser_ID")
    private User user;

}
