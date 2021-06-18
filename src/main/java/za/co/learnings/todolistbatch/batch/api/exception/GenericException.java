package za.co.learnings.todolistbatch.batch.api.exception;

import lombok.Getter;

@Getter
public class GenericException extends Exception {

    private Integer errorCode;

    public GenericException() {
    }
    public GenericException(String message) {
        super(message);
    }

    public GenericException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
