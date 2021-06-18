package za.co.learnings.todolistbatch.service;

import javassist.NotFoundException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import za.co.learnings.todolistbatch.batch.api.exception.ConflictException;

import static java.time.LocalDateTime.now;
import static java.time.ZoneOffset.UTC;

@Service
public class BatchService {

    private final JobLauncher jobLauncher;
    private final JobOperator jobOperator;

    @Qualifier("overdueTasksJob")
    private final Job overdueTasksJob;

    public BatchService(JobLauncher jobLauncher,
                        JobOperator jobOperator,
                        Job overdueTasksJob) {
        this.jobLauncher = jobLauncher;
        this.jobOperator = jobOperator;
        this.overdueTasksJob = overdueTasksJob;
    }

    public void stopBatchJob(Long batchId) throws NotFoundException, ConflictException {
        try {
            jobOperator.stop(batchId);
        } catch (JobExecutionNotRunningException e) {
            e.printStackTrace();
            throw new ConflictException("Requested batch job with id ("+ batchId + ") is not  currently running");
        } catch (NoSuchJobExecutionException e) {
            e.printStackTrace();
            throw new NotFoundException("Requested batch job with id ("+ batchId + ") does not exist");
        }
    }

    private void run(Job job, JobParameters jobParameters) {
        new Thread(() -> {
            try {
                jobLauncher.run(job, jobParameters);
            } catch (JobExecutionAlreadyRunningException | JobParametersInvalidException | JobInstanceAlreadyCompleteException | JobRestartException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void startOverdueTasksJob() {
        var jobParameters = new JobParametersBuilder()
                .addLong("epocStamp", now().toEpochSecond(UTC))
                .toJobParameters();
        this.run(overdueTasksJob, jobParameters);
    }
}
