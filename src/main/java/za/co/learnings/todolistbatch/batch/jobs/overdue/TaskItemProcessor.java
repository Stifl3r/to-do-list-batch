package za.co.learnings.todolistbatch.batch.jobs.overdue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class TaskItemProcessor implements ItemProcessor<Task, Task> {

    @Override
    public Task process(Task item) {
        log.info("Process task " + item.getName());
        return item;
    }
}
