package io.devzona.springboot.emailbatch.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailStepExecutionListener {

    @BeforeStep
    void beforeStep(StepExecution stepExecution) {
        log.info("BeforeStep Execution Listener {}", stepExecution.getStepName());
    }

    @AfterStep
    ExitStatus afterStep(StepExecution stepExecution) {
        log.info("AfterStep Execution Listener {} ", stepExecution.getStepName());
        return stepExecution.getExitStatus();
    }
}
