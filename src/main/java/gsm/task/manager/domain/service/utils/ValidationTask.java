package gsm.task.manager.domain.service.utils;

import gsm.task.manager.domain.enums.StatusTask;
import gsm.task.manager.domain.model.Task;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class ValidationTask {

    public static void updateData(Task taskToUpdate, Task taskUpdated) {
        //update title
        if(!taskUpdated.getTitle().isEmpty())
            taskToUpdate.setTitle(taskUpdated.getTitle());

        //update description
        if(!taskUpdated.getShortDescription().isEmpty()) {
            taskUpdated.setShortDescription(taskUpdated.getShortDescription());
        }

        //update priority
        if(taskUpdated.getPriority().getDescription() != null) {
            taskToUpdate.setPriority(taskUpdated.getPriority());
        }

        //udpdate category
        if(taskUpdated.getCategory().getDescription() != null) {
            taskToUpdate.setPriority(taskUpdated.getPriority());
        }

        //update datetime
        taskToUpdate.setDatetimeLimit(taskUpdated.getDatetimeLimit());
    }

    public static Task validateStatus(Task task) {
        if(task.getDatetimeLimit().isAfter(LocalDateTime.now()) && task.getStatus() != StatusTask.DONE)
            task.setStatus(StatusTask.TO_DO);
        else task.setStatus(StatusTask.LATE);
        return task;
    }

    public static void convertToLocalDate(Task task) {
        ZonedDateTime zoneGlobal = task.getDatetimeLimit().atZone(ZoneId.of("GMT"));
        ZonedDateTime zoneLocal = zoneGlobal.withZoneSameInstant(ZoneId.systemDefault());
        task.setDatetimeLimit(zoneLocal.toLocalDateTime());
    }

    public static void convertToGlobalDate(Task task) {
        ZonedDateTime zoneLocal = task.getDatetimeLimit().atZone(ZoneId.systemDefault());
        ZonedDateTime zoneGlobal = zoneLocal.withZoneSameInstant(ZoneId.of("GMT"));
        task.setDatetimeLimit(zoneGlobal.toLocalDateTime());
    }
}
