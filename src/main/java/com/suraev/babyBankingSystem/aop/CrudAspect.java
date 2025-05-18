package com.suraev.babyBankingSystem.aop;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import com.suraev.babyBankingSystem.aop.annotation.OperationLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.util.StopWatch;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class CrudAspect {

    @Around("@annotation(operationLog)")
    public Object logOperation(ProceedingJoinPoint joinPoint, OperationLog operationLog) throws Throwable {

        String methodName = joinPoint.getSignature().getName();

        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        Object[] args = joinPoint.getArgs();    

        log.info("Starting operation: {} in : {}", operationLog.operation(), className, Arrays.toString(args));
        
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try{
            Object result = joinPoint.proceed();
            stopWatch.stop();
            log.info("Operation: {} completed in {} ms", methodName, stopWatch.getTotalTimeMillis());
            return result;
        } catch (Exception e) {
            log.error("Failder  operation: {} in {} ms, error: {}", operationLog.operation(), stopWatch.getTotalTimeMillis(), Arrays.toString(args), e.getMessage());
            throw e;
        }   
    }
}
