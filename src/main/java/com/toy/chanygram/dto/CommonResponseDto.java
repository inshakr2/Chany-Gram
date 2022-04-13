package com.toy.chanygram.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponseDto<T> {
    private int code; // 1(성공) -1(실패)
    private String message;
    private T data;
}
