package com.toy.chanygram.handler;

import com.toy.chanygram.exception.CustomValidationApiException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class ValidationAdvice {

    @Around("execution(* com.toy.chanygram.controller.api.*Controller.*(..))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    for(FieldError fieldError : bindingResult.getFieldErrors()) {
                        errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                        log.info("유효성 검사 실패 [" + fieldError.getField() +"] : [" + fieldError.getDefaultMessage() + "]");
                    }

                    throw new CustomValidationApiException("유효성 검사 실패", errorMap);
                }

            }
        }
        return proceedingJoinPoint.proceed();
    }

    @Around("execution(* com.toy.chanygram.controller.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                System.out.println("유효성 검사 진행");
            }
        }
        return proceedingJoinPoint.proceed();
    }
}
