package za.co.learnings.todolistbatch.batch.jobs.overdue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@EnableBatchProcessing
@Slf4j
public class OverdueTasksStepOneConfiguration {

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope
    public OverdueTasksStepOneListener overdueTasksStepOneListener() {
        return new OverdueTasksStepOneListener();
    };

    public OverdueTasksStepOneConfiguration(StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    @StepScope
    public ItemReader<Task> taskItemReader() {
        return new ListItemReader<>(Collections.emptyList());
    }

    @Bean
    public ItemProcessor<Task, Task> taskItemProcessor() {
        return new TaskItemProcessor();
    }

    @Bean
    @StepScope
    public ItemWriter<Task> taskItemWriter() {
        return new TaskItemWriter();
    }

    @Bean
    public Step overdueTasksStep1(ItemReader<Task> taskItemReader,
                                  ItemProcessor<Task, Task> taskItemProcessor,
                                  ItemWriter<Task> taskItemWriter) {
        return stepBuilderFactory.get("overdueTasksStep1")
                .listener(overdueTasksStepOneListener())
                .<Task, Task> chunk(10)
                .reader(taskItemReader)
                .processor(taskItemProcessor)
                .writer(taskItemWriter)
                .build();
    }
}
