package gsm.task.manager.controller.exception;

import gsm.task.manager.domain.exceptions.StandardErrorObject;
import gsm.task.manager.domain.exceptions.SubTaskException;
import gsm.task.manager.domain.exceptions.TaskNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<StandardErrorObject> handlerTaskNotFound(TaskNotFoundException exc, HttpServletRequest req) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardErrorObject seo = createObjectError("Task Exception", status, exc, req);
        return ResponseEntity.status(status).body(seo);
    }

    @ExceptionHandler(SubTaskException.class)
    public ResponseEntity<StandardErrorObject> handlerSubtaskException(SubTaskException exc, HttpServletRequest req) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardErrorObject seo = createObjectError("Subtask Exception", status, exc, req);
        return ResponseEntity.status(status).body(seo);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardErrorObject> handlerDTOException(HttpMessageNotReadableException  exc, HttpServletRequest req) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardErrorObject seo = createObjectError("Invalid Json", status, exc, req);
        return ResponseEntity.status(status).body(seo);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<StandardErrorObject> handlerOtherExceptions(Throwable exc, HttpServletRequest req) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardErrorObject seo = createObjectError("Server error", status, exc, req);
        return ResponseEntity.status(status).body(seo);
    }

    private StandardErrorObject createObjectError(String error, HttpStatus status, Throwable exc, HttpServletRequest req) {
        return new StandardErrorObject(Instant.now()
                , status.value()
                , error
                , exc.getMessage()
                , req.getRequestURI());
    }
}
