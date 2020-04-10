package io.devzona.springboot.emailbatch.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.AfterChunkError;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailChunkListener {

    @BeforeChunk
    public void beforeChunk(ChunkContext context) {
        log.info("BeforeChunk Listener {} ", context.getStepContext());
    }

    @AfterChunk
    void afterChunk(ChunkContext context) {
        log.info("AfterChunk Listener {} ", context.getStepContext());
    }

    @AfterChunkError
    void afterFailedChunk(ChunkContext context) {
        log.info("AfterChunkError Listener {} ", context.getStepContext());
    }
}
