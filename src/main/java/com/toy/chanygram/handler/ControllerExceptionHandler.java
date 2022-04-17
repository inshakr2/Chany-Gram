package com.toy.chanygram.handler;

import com.toy.chanygram.dto.CommonResponseDto;
import com.toy.chanygram.exception.CustomValidationApiException;
import com.toy.chanygram.exception.CustomValidationException;
import com.toy.chanygram.utils.ScriptWriter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationApiException(CustomValidationApiException e) {

        return new ResponseEntity<>(
                new CommonResponseDto(-1, e.getMessage(), e.getErrorMap()),
                HttpStatus.BAD_REQUEST
        );
    }
}
