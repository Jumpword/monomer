package cn.wolves.common.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jumping
 */
@Aspect
@Configuration
@Slf4j
public class MethodSpendAspect {

    @Pointcut("@annotation(cn.wolves.common.annotation.SpendTime)")
    public void spendTime() {
    }


    @Around("spendTime()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        try {
            Object result = point.proceed();
            saveLog(point, beginTime, 0);
            return result;
        } catch (Throwable e) {
            saveLog(point, beginTime, 1);
            throw e;
        }

    }

    protected void saveLog(ProceedingJoinPoint joinPoint, long beginTime, int type) {
        long time = System.currentTimeMillis() - beginTime;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        StringBuilder logBuilder = new StringBuilder(2000);
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getMethod().getName();
        logBuilder.append(className);
        logBuilder.append(".");
        logBuilder.append(methodName);
        logBuilder.append("(");
        Object[] args = joinPoint.getArgs();
        try {
            logBuilder.append(JSON.toJSONString(args, false));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        logBuilder.append(")");
        logBuilder.append("花费总时间：");
        logBuilder.append(time);
        logBuilder.append("毫秒");
        if (type == 0) {
            log.warn(logBuilder.toString());
        } else {
            log.error(logBuilder.toString());
        }

    }


}
