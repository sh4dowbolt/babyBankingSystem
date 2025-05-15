package com.suraev.babyBankingSystem.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import com.suraev.babyBankingSystem.aop.annotation.FinancialLog;
import org.aspectj.lang.ProceedingJoinPoint;
import com.suraev.babyBankingSystem.dto.TransferRequest;


@Aspect 
@Component
@Slf4j
public class FinancialAspect {

    @Around("@annotation(financialLog)")
    public Object logFinancialOperation(ProceedingJoinPoint joinPoint, FinancialLog financialLog) throws Throwable {
       
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        if(args[0] instanceof TransferRequest request){
           log.info("Starting financial operation: {} from user {} to user {} with amount: {}", 
           financialLog.operation(), 
           request.getSourceUserId(), 
           request.getTargetUserId(), 
           request.getValue());
        }

        try {
            Object result = joinPoint.proceed();
            log.info("Successfully completed financial operation: {}", methodName);
            return result;
        } catch (Throwable e) { 
            log.error("Failed financial operation: {}, error: {}", methodName, e.getMessage());
           throw e;
        }
    }

}