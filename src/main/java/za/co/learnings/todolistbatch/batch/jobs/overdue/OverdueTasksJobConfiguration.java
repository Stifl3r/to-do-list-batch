package za.co.learnings.todolistbatch.batch.jobs.overdue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@Slf4j
public class OverdueTasksJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final JobRepository jobRepository;

    public OverdueTasksJobConfiguration(JobBuilderFactory jobBuilderFactory,
                                        JobRepository jobRepository) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.jobRepository = jobRepository;
    }

    @Bean(name = "overdueTasksJob")
    public Job overdueTicketsJob(Step overdueTasksStep1) {
        return jobBuilderFactory.get("overdueTasksJob")
                .repository(jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(overdueTasksStep1)
                .end().build();

    }
}
