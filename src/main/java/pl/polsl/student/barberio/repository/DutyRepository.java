package pl.polsl.student.barberio.repository;

import org.springframework.data.repository.CrudRepository;
import pl.polsl.student.barberio.model.Duty;

public interface DutyRepository extends CrudRepository<Duty, Long> {
}
