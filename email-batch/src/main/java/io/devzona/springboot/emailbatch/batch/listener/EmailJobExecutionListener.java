package io.devzona.springboot.emailbatch.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailJobExecutionListener extends JobExecutionListenerSupport {

    @BeforeJob
    public void beforeJob(JobExecution jobExecution) {
        log.info("BeforeJob - started Listener before Job");
        log.info(jobExecution.getJobInstance().getJobName());
    }

    @AfterJob
    public void afterJob(JobExecution jobExecution) {
        log.info("Listener AfterJob - ");
        log.info(jobExecution.getJobInstance().getJobName());
        if (jobExecution.getStatus() == BatchStatus.FAILED || jobExecution.getStatus() == BatchStatus.ABANDONED ||
                jobExecution.getStatus() == BatchStatus.UNKNOWN) {
            log.info("ABORTED");
        } else {
            log.info("COMPLETED");

        }
        //System.exit(0);
    }
}
