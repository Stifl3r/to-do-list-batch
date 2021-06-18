package za.co.learnings.todolistbatch.batch.jobs.overdue;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Task {
    private String name;
    private String description;
    private String status;
    private LocalDateTime statusUpdate;
    private String deadline;
    private EmployeeModel assignee;
}
