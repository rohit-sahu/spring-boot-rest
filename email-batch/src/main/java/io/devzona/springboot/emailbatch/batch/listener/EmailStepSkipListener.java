package io.devzona.springboot.emailbatch.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailStepSkipListener<T,S> {

    @OnSkipInRead
    void onSkipInRead(Throwable t) {
        log.info("OnSkipInRead Listener - is called whenever an item is skipped while reading. {}", t.getMessage());
    }

    @OnSkipInProcess
    void onSkipInProcess(T item, Throwable t) {
        log.info("OnSkipInProcess Listener -  {}", item);
    }

    @OnSkipInWrite
    void onSkipInWrite(S item, Throwable t) {
        log.info("OnSkipInWrite Listener - is called when an item is skipped while writing. {}", item);
    }
}
