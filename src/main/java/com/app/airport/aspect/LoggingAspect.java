package com.app.airport.aspect;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
  private final int MAX_LENGTH = 90;

  @Pointcut("execution(* com.app.airport.service.*.*(..)) || within(@org.springframework.stereotype.Repository *)")
  public void debugLog() {}

  @AfterThrowing(value = "debugLog()", throwing = "throwable")
  public void afterThrow(JoinPoint joinPoint, Throwable throwable) {
    CodeSignature methodSignature = (CodeSignature) joinPoint.getSignature();
    log.error(
        "{} throws {} : {} \nCause : {}",
        methodSignature.getName(),
        throwable.getClass().getSimpleName(),
        throwable.getMessage(),
        throwable.getCause());
  }

  @AfterReturning(value = "debugLog()", returning = "result")
  public void afterReturning(JoinPoint joinPoint, Object result) {
    CodeSignature methodSignature = (CodeSignature) joinPoint.getSignature();
    if (((MethodSignature) methodSignature).getReturnType().toString().equals("void")) {
      log.debug("Method {} was correctly completed", methodSignature.getName());
    } else {
      if (nonNull(result)) {
        if (result.toString().length() < MAX_LENGTH) {
          log.debug(
              "Method {} was correctly completed, and return:\n\t- {}",
              methodSignature.getName(),
              result);
        } else {
          log.debug(
              "Method {} was correctly completed, and return {}",
              methodSignature.getName(),
              result.getClass().getSimpleName());
        }
      } else {
        log.debug(
            "Method {} was correctly completed, and return 'null' value",
            methodSignature.getName());
      }
    }
  }

  @Before("debugLog()")
  public void before(JoinPoint joinPoint) {
    CodeSignature methodSignature = (CodeSignature) joinPoint.getSignature();
    StringBuilder builder;
    if (joinPoint.getArgs().length == 0) {
      builder =
          new StringBuilder("Method ")
              .append(methodSignature.getName())
              .append(" run with no args");
    } else {
      builder =
          new StringBuilder("Method ").append(methodSignature.getName()).append(" run with args:");
      Object[] args = joinPoint.getArgs();
      List<String> sigParamNames = Arrays.asList(methodSignature.getParameterNames());
      for (int i = 0; i < sigParamNames.size(); i++) {
        builder
            .append("\n\t")
            .append(sigParamNames.get(i))
            .append(" = ")
            .append(
                nonNull(args[i]) && args[i].toString().length() > MAX_LENGTH
                    ? args[i].getClass().getSimpleName() + " {...}"
                    : args[i]);
      }
    }
    log.debug("{} in class - {}", builder, methodSignature.getDeclaringTypeName());
  }
}
