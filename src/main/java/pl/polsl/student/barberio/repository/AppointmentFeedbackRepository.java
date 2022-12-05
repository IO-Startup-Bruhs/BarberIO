package pl.polsl.student.barberio.repository;

import org.springframework.data.repository.CrudRepository;
import pl.polsl.student.barberio.model.AppointmentFeedback;

public interface AppointmentFeedbackRepository extends CrudRepository<AppointmentFeedback, Long> {
}
