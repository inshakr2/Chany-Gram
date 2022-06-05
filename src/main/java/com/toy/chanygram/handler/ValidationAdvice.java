package com.toy.chanygram.handler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidationAdvice {

    @Around("execution(* com.toy.chanygram.controller.api.*Controller.*(..))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("ValidationAdvice.apiAdvice");
        return proceedingJoinPoint.proceed();
    }

    @Around("execution(* com.toy.chanygram.controller.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("ValidationAdvice.advice");
        return proceedingJoinPoint.proceed();
    }
}
