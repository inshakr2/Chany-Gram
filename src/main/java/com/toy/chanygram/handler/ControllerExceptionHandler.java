package com.toy.chanygram.handler;

import com.toy.chanygram.exception.CustomValidationException;
import com.toy.chanygram.utils.ScriptWriter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e) {
//        CommonResponseDto commonResponseDto = new CommonResponseDto(-1, e.getMessage(), e.getErrorMap());
        return ScriptWriter.alertWithBack(e.getErrorMap().toString());
    }
}
