package io.devzona.springboot.emailbatch.config;

import io.devzona.springboot.emailbatch.batch.listener.*;
import io.devzona.springboot.emailbatch.batch.step.MyTaskOne;
import io.devzona.springboot.emailbatch.batch.step.MyTaskTwo;
import io.devzona.springboot.emailbatch.entity.Employee;
import io.devzona.springboot.emailbatch.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private EmployeeService service;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step emailSend(@Autowired ItemReader<Employee> itemReader,
                          @Autowired ItemProcessor<Employee, Employee> itemProcessor,
                          @Autowired ItemWriter<Employee> itemWriter,
                          @Autowired EmailStepExecutionListener emailStepExecutionListener,
                          @Autowired EmailReadListener emailReadListener,
                          @Autowired EmailProcessListener emailProcessListener,
                          @Autowired EmailWriteListener emailWriteListener,
                          @Autowired EmailChunkListener emailChunkListener,
                          @Autowired EmailStepSkipListener emailStepSkipListener) {
        return stepBuilderFactory.get("ETL-email-send")
                .<Employee, Employee>chunk(100)
                .listener(emailStepExecutionListener)
                .listener(emailReadListener)
                .listener(emailProcessListener)
                .listener(emailWriteListener)
                .listener(emailChunkListener)
                .listener(emailStepSkipListener)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    @Bean
    @StepScope
    public IteratorItemReader<Employee> readEmployee() {
        List<Employee> employees = new ArrayList<>();
        try {
            log.info("IteratorItemReader .......");
            employees = service.getAllEmployees();
            log.info("All Employees {}", employees.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new IteratorItemReader<>(employees);
    }

    @Bean
    public Job job(@Autowired @Qualifier("emailSend") Step step,
                   @Autowired EmailJobExecutionListener emailJobExecutionListener) {
        return jobBuilderFactory.get("ETL-Load")
                .listener(emailJobExecutionListener)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public Step stepOne() {
        return stepBuilderFactory.get("stepOne")
                .tasklet(new MyTaskOne())
                .build();
    }

    @Bean
    public Step stepTwo() {
        return stepBuilderFactory.get("stepTwo")
                .tasklet(new MyTaskTwo())
                .build();
    }

    @Bean
    public Job demoJob() {
        return jobBuilderFactory.get("demoJob")
                .incrementer(new RunIdIncrementer())
                .start(stepOne())
                .next(stepTwo())
                .build();
    }
}
