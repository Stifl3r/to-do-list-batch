package za.co.learnings.todolistbatch.batch.api.exception;

import lombok.Getter;

@Getter
public class ConflictException extends GenericException {
    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, int errorCode) {
        super(message, errorCode);
    }
}
