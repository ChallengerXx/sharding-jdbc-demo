package com.xx.aop;

import com.alibaba.fastjson.JSON;
import com.xx.annotation.MethodDesc;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class MethodInvokeAop {


    /**
     * 拦截controller下任意类任意方法执行aop
     * 拦截任意配置注解MethodDesc方法
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.xx.controller..*.*(..))+ @annotation(com.xx.annotation.MethodDesc)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        MethodDesc methodDesc = method.getAnnotation(MethodDesc.class);
        String methodDescription = "";
        if (method != null){
            methodDescription = methodDesc.value();
        }
        log.info("{}-{}请求报文:\r\n{}", methodDescription, method.getName(), JSON.toJSONString(joinPoint.getArgs()));
        Object result = joinPoint.proceed();
        log.info("{}-{}响应报文:\n\n{}", methodDescription, method.getName(), JSON.toJSONString(result));
        return result;
    }
}
