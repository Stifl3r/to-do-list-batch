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
import za.co.learnings.todolistbatch.service.TodoApiService;

@Configuration
@EnableBatchProcessing
@Slf4j
public class OverdueTasksStepOneConfiguration {

    private final StepBuilderFactory stepBuilderFactory;
    private final TodoApiService todoApiService;

    @Bean
    @StepScope
    public OverdueTasksStepOneListener overdueTasksStepOneListener() {
        return new OverdueTasksStepOneListener();
    };

    public OverdueTasksStepOneConfiguration(StepBuilderFactory stepBuilderFactory,
                                            TodoApiService todoApiService) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.todoApiService = todoApiService;
    }

    @Bean
    @StepScope
    public ItemReader<Task> taskItemReader() {
        var result = todoApiService.getOverDueTasks();
        log.info("Total number of tasks read: " + result.size());
        return new ListItemReader<>(result);
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
