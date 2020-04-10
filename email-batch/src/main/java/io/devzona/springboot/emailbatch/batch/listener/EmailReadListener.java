package io.devzona.springboot.emailbatch.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.AfterRead;
import org.springframework.batch.core.annotation.BeforeRead;
import org.springframework.batch.core.annotation.OnReadError;
import org.springframework.stereotype.Component;


/**
 * Listener for the Email read.
 *
 * @version 1.0
 * @since 08-04-2020
 * @author rohit-sahu
 * @see org.springframework.batch.core.step.builder.SimpleStepBuilder
 * @see org.springframework.batch.core.step.builder.SimpleStepBuilder#listener(Object)
 */
@Slf4j
@Component
public class EmailReadListener<T> {

    @BeforeRead
    public void beforeRead() {
        log.info("BeforeRead Listener - The beforeRead method is called before each call to read on the ItemReader.");
    }

    @AfterRead
    public void afterRead(T item) {
        log.info("AfterRead Listener - The afterRead method is called after each successful call to read and is passed the item that was read");
    }

    @OnReadError
    public void onReadError(Exception ex) {
        log.info("OnReadError Listener - If there was an error while reading, the onReadError method is called.");
    }
}
