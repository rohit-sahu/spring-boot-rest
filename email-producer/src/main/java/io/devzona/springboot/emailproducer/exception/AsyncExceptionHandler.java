package io.devzona.springboot.emailproducer.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * Exception handler class for Async methods.
 *
 * @author Rohit.Sahu
 */
@Slf4j
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.error("Exception Caught in Thread - {}", Thread.currentThread().getName());
        log.error("Exception message - {}", ex.getMessage());
        log.error("Method name - {}", method.getName());
        for (Object param : params) {
            log.error("Parameter value - {}", param);
        }
    }
}
