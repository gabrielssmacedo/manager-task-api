package gsm.task.manager.domain.exceptions;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(String taskTitle) {
        super(String.format("Task error: %s.", taskTitle));
    }
}
