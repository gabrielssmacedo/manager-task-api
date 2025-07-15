package gsm.task.manager.repository;

import gsm.task.manager.domain.model.SubTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubTaskRespository extends JpaRepository<SubTask, Long> {
}
