package za.co.learnings.todolistbatch.batch.jobs.overdue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import za.co.learnings.todolistbatch.service.EmailService;

import java.util.List;

@Slf4j
public class TaskItemWriter implements ItemWriter<Task> {

    @Autowired
    private EmailService emailService;

    @Override
    public void write(List<? extends Task> items) {

        for(Task task: items) {
            var message = "Hi "+ task.getAssignee().getFirstname() + " " + task.getAssignee().getLastname()+"\n\n" +
                    "Please note that your ticket " + task.getName() + " is over due. \n" +
                    "The current status of the ticket is " +task.getStatus() + " and last recorded activity was on " + task.getStatusUpdate() + "\n\n" +
                    "Regards\n" +
                    "Gate Keeper";

            emailService.sendEmail(task.getAssignee().getEmail(),
                    "Over Due Ticket",
                    message,
                    "");
            log.info(message);
        }
    }
}
