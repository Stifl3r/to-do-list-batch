package za.co.learnings.todolistbatch.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import za.co.learnings.todolistbatch.batch.jobs.overdue.Task;

import java.util.Arrays;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Service
public class TodoApiService {

    private final RestTemplate restTemplate;

    public TodoApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Task> getOverDueTasks() {
        var headers = new HttpHeaders();
        var baseUrl = "http://localhost:8080/api";

        var builder = fromHttpUrl(baseUrl + "/tasks")
                .queryParam("overdue", true);
        var request = new HttpEntity<>(headers);
        var responseEntity = restTemplate.exchange(
                builder.build().toUri(), GET, request, Task[].class);
        return Arrays.asList(requireNonNull(responseEntity.getBody()));

    }
}
