package com.rhb.assessment.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.rhb.assessment.controller..*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        // Log request
        logger.info("Request: {} {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

        // Proceed with method execution
        Object result = joinPoint.proceed();

        // Log response
        logger.info("Response: {}", result);

        return result;
    }
}
