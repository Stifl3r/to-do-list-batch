package za.co.learnings.todolistbatch.batch.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.Map;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class JobConfig {

    private String name;

    @Singular("property")
    private Map<String, String> properties;

    private boolean asynchronous;
}
