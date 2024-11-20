package com.reporte.empleo.Batch.config;

import com.reporte.empleo.Batch.ReporteBatch;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ReporteBatch reporteBatch;

    public BatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager, ReporteBatch reporteBatch) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.reporteBatch = reporteBatch;
    }

    @Bean
    public Job exportJob() {
        return new JobBuilder("exportJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(jobExecutionListener())
                .start(reportStep())
                .build();
    }

    @Bean
    public Step reportStep() {
        return new StepBuilder("reportStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    reporteBatch.generateReports(); // Llama al método para generar reportes
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }


    @Bean
    public Step step2() {
        return new StepBuilder("step2", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Ejecutando paso 2");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
    @Bean
    public JobExecutionListener jobExecutionListener() {
        return new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) {
                System.out.println("Antes de ejecutar el trabajo: " + jobExecution.getJobInstance().getJobName());
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
                System.out.println("Después de ejecutar el trabajo: " + jobExecution.getJobInstance().getJobName());
            }
        };
    }
}
