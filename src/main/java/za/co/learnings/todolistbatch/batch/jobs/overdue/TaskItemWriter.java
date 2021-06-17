package za.co.learnings.todolistbatch.batch.jobs.overdue;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class TaskItemWriter implements ItemWriter<Task> {

    @Override
    public void write(List<? extends Task> items) throws Exception {

    }
}
