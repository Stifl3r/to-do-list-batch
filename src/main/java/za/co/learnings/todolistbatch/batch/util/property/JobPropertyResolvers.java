package za.co.learnings.todolistbatch.batch.util.property;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.stereotype.Component;
import za.co.learnings.todolistbatch.batch.util.JobConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Slf4j
@Component
public class JobPropertyResolvers implements Consumer<JobExecution> {

    public static JobPropertyResolvers JobProperties;

    private final Environment env;
    private final Map<String, JobPropertyResolver> resolvers = new ConcurrentHashMap<>();

    public JobPropertyResolvers(Environment env, JobExecutionAspect jobExecutionAspect) {
        this.env = env;
        jobExecutionAspect.register(this);
        JobProperties = this;
    }

    public PropertyResolver of(String jobName) {
        JobPropertyResolver jobPropertyResolver = resolvers.get(jobName);
        return jobPropertyResolver == null ? env : jobPropertyResolver;
    }

    public void started(JobConfig jobConfig) {
        String jobName = jobConfig.getName();
        JobPropertyResolver resolver = new JobPropertyResolver(jobConfig, env);
        resolvers.put(jobName, resolver);
        log.info("Enabled {}", resolver);
    }

    @Override
    public void accept(JobExecution je) {
        if (!je.isRunning()) {
            JobPropertyResolver resolver = resolvers.remove(je.getJobInstance().getJobName());
            if (resolver != null)
                log.info("Disabled {}", je.getJobInstance().getJobName());
        }
    }
}
