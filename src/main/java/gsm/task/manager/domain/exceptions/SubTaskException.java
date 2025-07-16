package gsm.task.manager.domain.exceptions;

public class SubTaskException extends RuntimeException{

    public SubTaskException(String message) {
        super("Subtask error: " + message);
    }
}
