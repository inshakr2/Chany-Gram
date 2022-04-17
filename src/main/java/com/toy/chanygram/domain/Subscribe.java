package com.toy.chanygram.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter(PROTECTED) @ToString
@NoArgsConstructor(access = PROTECTED)
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "subscribe_uk",
                            columnNames = {"FromUser_ID", "ToUser_ID"}
                )
        }
)
public class Subscribe extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FromUser_ID")
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "ToUser_ID")
    private User toUser;
}
