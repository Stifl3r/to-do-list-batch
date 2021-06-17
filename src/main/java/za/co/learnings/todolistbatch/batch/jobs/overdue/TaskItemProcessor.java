package za.co.learnings.todolistbatch.batch.jobs.overdue;

import org.springframework.batch.item.ItemProcessor;

public class TaskItemProcessor implements ItemProcessor<Task, Task> {

    @Override
    public Task process(Task item) throws Exception {
        return item;
    }
}
