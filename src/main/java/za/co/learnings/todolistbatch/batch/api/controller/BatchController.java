package za.co.learnings.todolistbatch.batch.api.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.learnings.todolistbatch.batch.api.exception.ConflictException;
import za.co.learnings.todolistbatch.service.BatchService;

@Tag(name = "batch")
@RestController
@RequestMapping(value = "/batch")
public class BatchController {

    private final BatchService batchService;

    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping("/overdueTasksTrigger")
    public ResponseEntity<String> triggerOverdueTasksJob() {
        batchService.startOverdueTasksJob();
        return ResponseEntity.ok(null);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "409", description = "Conflict")
    })
    @PostMapping("/stopBatchJob")
    public void stopBatchJob(@RequestParam("batchId") Long batchId) throws NotFoundException, ConflictException {
        batchService.stopBatchJob(batchId);
    }
}
