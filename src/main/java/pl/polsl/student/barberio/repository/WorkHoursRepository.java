package pl.polsl.student.barberio.repository;

import org.springframework.data.repository.CrudRepository;
import pl.polsl.student.barberio.model.WorkHours;

public interface WorkHoursRepository extends CrudRepository<WorkHours, Long> {
}
