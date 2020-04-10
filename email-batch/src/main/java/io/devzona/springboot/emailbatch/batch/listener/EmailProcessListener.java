package io.devzona.springboot.emailbatch.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.AfterProcess;
import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.core.annotation.OnProcessError;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailProcessListener<T, S> {

    @BeforeProcess
    public void beforeProcess(T item) {
        log.info("BeforeProcess Listener");
    }

    @AfterProcess
    public void afterProcess(T item, S result) {
        log.info("AfterProcess Listener");
    }

    @OnProcessError
    public void onProcessError(T item, Exception e) {
        log.info("OnProcessError Listener");
    }
}
